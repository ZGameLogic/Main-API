package com.zgamelogic.dashboard;

import com.zgamelogic.dashboard.api.*;
import com.zgamelogic.dashboard.database.DashboardProject;
import com.zgamelogic.dashboard.database.DashboardProjectRepository;
import com.zgamelogic.dataotter.DataOtterApplication;
import com.zgamelogic.dataotter.DataOtterService;
import com.zgamelogic.github.GithubService;
import com.zgamelogic.github.data.GithubEnvironment;
import com.zgamelogic.github.data.GithubProject;
import com.zgamelogic.github.data.GithubRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class DashboardService {
    private final GithubService githubService;
    private final DataOtterService  dataOtterService;
    private final DashboardProjectRepository projectRepository;
    private final DashboardService selfProxy;

    public DashboardService(@Lazy DashboardService selfProxy, GithubService githubService, DataOtterService dataOtterService, DashboardProjectRepository projectRepository) {
        this.selfProxy = selfProxy;
        this.githubService = githubService;
        this.dataOtterService = dataOtterService;
        this.projectRepository = projectRepository;
    }

    public List<GitHubNameId> getGihubRepositoryList(){
        return githubService.getRepos().stream().map(repo -> new GitHubNameId(repo.name(), repo.id())).toList();
    }

    public List<DashboardProject> getDashboardProjects(){
        return projectRepository.findAll();
    }

    public SseEmitter getGitRichData(){
        SseEmitter emitter = new SseEmitter();
        selfProxy.getGitRichData(emitter);
        return emitter;
    }

    @Async
    public void getGitRichData(SseEmitter emitter){
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        // Github repository section
        List<GithubRepository> githubRepositories = githubService.getRepos();
        Set<Long> githubRepoLinks = projectRepository.findAllGithubRepositoryLinks();
        githubRepositories.stream().filter(repo -> githubRepoLinks.contains(repo.id())).forEach(repo ->
            sendEmitterMessage(emitter, new EmitterMessage(EmitterMessageType.DATA, repo))
        );
        tasks.addAll(githubRepositories.stream()
            .filter(repo -> githubRepoLinks.contains(repo.id()))
            .map(repo -> selfProxy.getGitRepoRichData(emitter, repo))
            .toList());

        //Github project section
        List<GithubProject> githubProjects = githubService.getProjects();
        Set<Long> githubProjectLinks = projectRepository.findAllGithubProjectLinks();
        githubProjects.stream().filter(project -> githubProjectLinks.contains(project.id())).forEach(project ->
            sendEmitterMessage(emitter, new EmitterMessage(EmitterMessageType.PROJECT_DATA, project))
        );
        tasks.addAll(githubProjects.stream()
            .filter(project -> githubProjectLinks.contains(project.id()))
            .map(project -> selfProxy.getGithubProjectsData(emitter, project))
            .toList());

        // DataOtter section
        Set<Long> dataOtterLinks = projectRepository.findAllDataOtterLinks();
        tasks.addAll(dataOtterLinks.stream()
            .map(applicationId -> selfProxy.getMonitoringRichData(emitter, applicationId))
            .toList());

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[]{})).whenComplete((_, _) -> {
            sendEmitterMessage(emitter, new EmitterMessage(EmitterMessageType.DONE, null));
            emitter.complete();
        });
    }

    @Async
    protected CompletableFuture<Void> getMonitoringRichData(SseEmitter emitter, long applicationId){
        DataOtterApplication application = dataOtterService.getDataOtterApplication(applicationId);
        if(application != null) sendEmitterMessage(emitter, new EmitterMessage(EmitterMessageType.MONITOR_DATA, new DataOtterRichApplicationDTO(application.id(), application.status())));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    protected CompletableFuture<Void> getGithubProjectsData(SseEmitter emitter, GithubProject githubProject){
        return CompletableFuture.completedFuture(null);
    }

    @Async
    protected CompletableFuture<Void> getGitRepoRichData(SseEmitter emitter, GithubRepository repo){
        GithubRichRepositoryDTO richGithubRepo = new GithubRichRepositoryDTO(repo.id());
        List<GithubEnvironment> environments = githubService.getRepoEnvironments(repo);

        environments.forEach(env ->
            githubService.getRepoDeployments(repo, env).stream().findFirst().ifPresentOrElse(deployment ->
                githubService.getDeploymentStatus(deployment).stream().findFirst().ifPresentOrElse(status ->
                    richGithubRepo.addEnvironment(env.name(), status.state())
                , () -> richGithubRepo.addEnvironment(env.name(), "unknown"))
            , () -> richGithubRepo.addEnvironment(env.name(), "unknown"))
        );

        richGithubRepo.setLanguages(githubService.getRepoLanguages(repo));
        githubService.getRepoReleases(repo).stream().findFirst().ifPresent(richGithubRepo::setRelease);
        richGithubRepo.setMilestones(githubService.getRepositoryMilestones(repo));
        sendEmitterMessage(emitter, new EmitterMessage(EmitterMessageType.RICH_DATA, richGithubRepo));
        return CompletableFuture.completedFuture(null);
    }

    private void sendEmitterMessage(SseEmitter emitter, EmitterMessage message){
        try {
            emitter.send(message);
        } catch (IOException _) {}
    }

    public DashboardProject createProject(CreateDashboardProjectDTO createDashboardProjectDTO) {
        return projectRepository.save(new DashboardProject(createDashboardProjectDTO));
    }

    public List<GitHubNameId> getGihubProjectList() {
        return githubService.getProjects().stream().map(project -> new GitHubNameId(project.title(), project.id())).toList();
    }

    public List<GitHubNameId> getGihubAspectList() {
        return projectRepository.findAllAdditionalAspects().stream().map(aspect -> new GitHubNameId(aspect, aspect)).toList();
    }
}

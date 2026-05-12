package com.zgamelogic.dashboard;

import com.zgamelogic.dashboard.api.CreateDashboardProjectDTO;
import com.zgamelogic.dashboard.api.EmitterMessage;
import com.zgamelogic.dashboard.api.EmitterMessageType;
import com.zgamelogic.dashboard.api.GithubRichRepositoryDTO;
import com.zgamelogic.dashboard.database.DashboardProject;
import com.zgamelogic.dashboard.database.DashboardProjectRepository;
import com.zgamelogic.github.GithubService;
import com.zgamelogic.github.data.GithubEnvironment;
import com.zgamelogic.github.data.GithubRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class DashboardService {
    private final GithubService githubService;
    private final DashboardProjectRepository projectRepository;
    private final DashboardService selfProxy;

    public DashboardService(@Lazy DashboardService selfProxy, GithubService githubService, DashboardProjectRepository projectRepository) {
        this.selfProxy = selfProxy;
        this.githubService = githubService;
        this.projectRepository = projectRepository;
    }

    public List<String> getGihubRepositoryList(){
        return githubService.getRepos().stream().map(GithubRepository::name).toList();
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
        List<GithubRepository> githubRepositories = githubService.getRepos();
        Set<Long> githubRepoLinks = projectRepository.findAllGithubRepositoryLinks();
        githubRepositories.stream().filter(repo -> githubRepoLinks.contains(repo.id())).forEach(repo -> {
            try {
                emitter.send(new EmitterMessage(EmitterMessageType.DATA, repo));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<Void>[] tasks = githubRepositories.stream()
            .filter(repo -> githubRepoLinks.contains(repo.id()))
            .map(repo -> selfProxy.getGitRepoRichData(emitter, repo))
            .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(tasks).whenComplete((_, _) -> {
            try {
                emitter.send(new EmitterMessage(EmitterMessageType.DONE, null));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            emitter.complete();
        });
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
        try {
            emitter.send(new EmitterMessage(EmitterMessageType.RICH_DATA, richGithubRepo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    public DashboardProject createProject(CreateDashboardProjectDTO createDashboardProjectDTO) {
        return projectRepository.save(new DashboardProject(createDashboardProjectDTO));
    }
}

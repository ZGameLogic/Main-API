package com.zgamelogic.dashboard;

import com.zgamelogic.dashboard.api.CreateDashboardProjectDTO;
import com.zgamelogic.dashboard.database.DashboardProject;
import com.zgamelogic.dashboard.database.DashboardProjectRepository;
import com.zgamelogic.github.GithubService;
import com.zgamelogic.github.data.GithubRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Set;

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
        Set<Long> githubRepoLinks = projectRepository.findAllGithubRepositoryLinks();
        Set<Long> githubProjectLinks = projectRepository.findAllGithubProjectLinks();
        // TODO implement
        emitter.complete();
    }

    public DashboardProject createProject(CreateDashboardProjectDTO createDashboardProjectDTO) {
        return projectRepository.save(new DashboardProject(createDashboardProjectDTO));
    }
}

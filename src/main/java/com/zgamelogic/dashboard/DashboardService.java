package com.zgamelogic.dashboard;

import com.zgamelogic.github.GithubService;
import com.zgamelogic.github.data.GithubRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
public class DashboardService {
    private final GithubService githubService;
    private final DashboardService selfProxy;

    public DashboardService(@Lazy DashboardService selfProxy, GithubService githubService) {
        this.selfProxy = selfProxy;
        this.githubService = githubService;
    }

    public List<String> getGihubRepositoryList(){
        return githubService.getRepos().stream().map(GithubRepository::name).toList();
    }

    public List<Object> getDashboardProjects(){
        // TODO implement
        return null;
    }

    public SseEmitter getDashboardProjectsRichData(){
        SseEmitter emitter = new SseEmitter();
        selfProxy.getProjectsRichData(emitter);
        return emitter;
    }

    @Async
    public void getProjectsRichData(SseEmitter emitter){
        // TODO get rich data and send it over when available
    }
}

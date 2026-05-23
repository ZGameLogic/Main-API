package com.zgamelogic.dashboard;

import com.zgamelogic.dashboard.api.CreateDashboardProjectDTO;
import com.zgamelogic.dashboard.api.GitHubNameId;
import com.zgamelogic.dashboard.database.DashboardProject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final String apiKey;

    public DashboardController(DashboardService dashboardService, @Value("${api-key}") String apiKey) {
        this.dashboardService = dashboardService;
        this.apiKey = apiKey;
    }

    @GetMapping("/github-repositories")
    public List<GitHubNameId> getRepositoryList(){
        return dashboardService.getGihubRepositoryList();
    }

    @GetMapping("/github-projects")
    public List<GitHubNameId> getProjectList(){
        return dashboardService.getGihubProjectList();
    }

    @GetMapping("/github-aspects")
    public List<GitHubNameId> getAspectList(){
        return dashboardService.getGihubAspectList();
    }

    @GetMapping("projects")
    public List<DashboardProject> getProjects(){
        return dashboardService.getDashboardProjects();
    }

    @GetMapping("projects/rich")
    public SseEmitter getProjectsRichData(){
        return dashboardService.getGitRichData();
    }

    @PostMapping("projects")
    public ResponseEntity<DashboardProject> createProject(@RequestBody CreateDashboardProjectDTO createDashboardProjectDTO, @RequestHeader(required = false) String key){
        if(!apiKey.equals(key)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok().body(dashboardService.createProject(createDashboardProjectDTO));
    }
}

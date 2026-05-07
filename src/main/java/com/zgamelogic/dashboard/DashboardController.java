package com.zgamelogic.dashboard;

import com.zgamelogic.dashboard.api.CreateDashboardProjectDTO;
import com.zgamelogic.dashboard.database.DashboardProject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("dashboard")
@AllArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/github-repositories")
    public List<String> getRepositoryList(){
        return dashboardService.getGihubRepositoryList();
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
    public DashboardProject createProject(@RequestBody CreateDashboardProjectDTO createDashboardProjectDTO){
        return dashboardService.createProject(createDashboardProjectDTO);
    }
}

package com.zgamelogic.dashboard;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Object> getProjects(){
        return dashboardService.getDashboardProjects();
    }

    @GetMapping("projects/rich")
    public SseEmitter getProjectsRichData(){
        return dashboardService.getDashboardProjectsRichData();
    }
}

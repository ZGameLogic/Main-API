package com.zgamelogic.dashboard;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
@AllArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
}

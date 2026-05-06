package com.zgamelogic.dashboard;

import com.zgamelogic.github.GithubService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardService {
    private final GithubService githubService;
}

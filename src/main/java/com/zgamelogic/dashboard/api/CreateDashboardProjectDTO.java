package com.zgamelogic.dashboard.api;

import java.util.Set;

public record CreateDashboardProjectDTO(
    String name,
    String description,
    Set<Long> githubProjectIds,
    Set<Long> githubRepoIds,
    Set<String> aspects
) {}

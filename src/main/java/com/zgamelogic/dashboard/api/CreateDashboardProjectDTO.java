package com.zgamelogic.dashboard.api;

import java.util.Set;

public record CreateDashboardProjectDTO(
    String name,
    String description,
    boolean favorite,
    Set<Long> githubProjectIds,
    Set<Long> githubRepoIds,
    Set<String> aspects
) {}

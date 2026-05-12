package com.zgamelogic.github.data;

import java.util.List;

public record GithubEnvironmentResponse(
        int total_count,
        List<GithubEnvironment> environments
) {}

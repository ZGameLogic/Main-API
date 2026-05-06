package com.zgamelogic.github.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record GithubDeployment(
        long id,
        String environment,
        @JsonProperty("statuses_url")
        String statusesUrl,
        @JsonProperty("created_at")
        Instant createdAt
) {}

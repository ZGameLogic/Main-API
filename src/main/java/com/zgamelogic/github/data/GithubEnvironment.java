package com.zgamelogic.github.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubEnvironment(
        long id,
        String name,
        @JsonProperty("html_url")
        String htmlUrl
) {}

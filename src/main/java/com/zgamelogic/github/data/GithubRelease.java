package com.zgamelogic.github.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubRelease(
        @JsonProperty("html_url")
        String htmlUrl,
        String name
) {}

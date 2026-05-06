package com.zgamelogic.github.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUser(
        String login,
        long id,
        @JsonProperty("avatar_url")
        String avatarUrl,
        @JsonProperty("html_url")
        String htmlUrl,
        String type
) {}

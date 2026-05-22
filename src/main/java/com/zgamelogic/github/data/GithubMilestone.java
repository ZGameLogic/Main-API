package com.zgamelogic.github.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubMilestone(
        @JsonProperty("html_url")
        String htmlUrl,
        long id,
        long number,
        String title,
        @JsonProperty("open_issues")
        long openIssues,
        @JsonProperty("closed_issues")
        long closedIssues
) {
}

package com.zgamelogic.dashboard.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "project_github_repository_link", schema = "api")
public class ProjectGithubRepositoryLink {
    @EmbeddedId
    private ProjectGithubRepositoryLinkId id;

    @MapsId("projectid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projectid", nullable = false)
    private DashboardProject projectId;


}
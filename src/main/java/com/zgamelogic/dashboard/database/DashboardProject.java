package com.zgamelogic.dashboard.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dashboard_projects", schema = "api")
public class DashboardProject {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project")
    private Set<GithubProjectLink> githubProjectLinks;

    @OneToMany(mappedBy = "project")
    private Set<GithubRepositoryLink> githubRepositoryLinks;

    public DashboardProject(String name, String description, HashSet<BigDecimal> githubProjectIds, HashSet<BigDecimal> githubRepoIds) {
        id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        githubProjectLinks = githubProjectIds.stream().map(gpi -> new GithubProjectLink(id, gpi)).collect(Collectors.toSet());
        githubRepositoryLinks = githubRepoIds.stream().map(gri -> new GithubRepositoryLink(id, gri)).collect(Collectors.toSet());

    }
}
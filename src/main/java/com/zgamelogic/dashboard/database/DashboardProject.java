package com.zgamelogic.dashboard.database;

import com.zgamelogic.dashboard.api.CreateDashboardProjectDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

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

    @ElementCollection
    @CollectionTable(name = "github_project_links", schema = "api",
            joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "github_project_id")
    private Set<Long> githubProjectLinks;

    @ElementCollection
    @CollectionTable(name = "github_repository_links", schema = "api",
            joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "github_repository_id")
    private Set<Long> githubRepositoryLinks;

    @ElementCollection
    @CollectionTable(name = "additional_project_aspects", schema = "api",
            joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "aspect")
    private Set<String> additionalAspects;

    public DashboardProject(CreateDashboardProjectDTO createDashboardProjectDTO) {
        this.id = UUID.randomUUID();
        this.description = createDashboardProjectDTO.description();
        this.name = createDashboardProjectDTO.name();
        this.githubProjectLinks = createDashboardProjectDTO.githubProjectIds();
        this.githubRepositoryLinks = createDashboardProjectDTO.githubRepoIds();
        this.additionalAspects = createDashboardProjectDTO.aspects();
    }
}
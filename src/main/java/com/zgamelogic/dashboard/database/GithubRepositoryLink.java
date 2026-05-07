package com.zgamelogic.dashboard.database;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "github_repository_link", schema = "api")
public class GithubRepositoryLink {
    @EmbeddedId
    private GithubRepositoryLinkId id;

    @MapsId("projectId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private DashboardProject project;

    public GithubRepositoryLink(UUID id, BigDecimal gri) {
        this.id = new GithubRepositoryLinkId(id, gri);
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @Embeddable
    public static class GithubRepositoryLinkId implements Serializable {
        @Serial
        private static final long serialVersionUID = 4435897051497440052L;
        @Column(name = "project_id", nullable = false)
        private UUID projectId;

        @Column(name = "github_repo_id", nullable = false, precision = 38, scale = 2)
        private BigDecimal githubRepoId;

        public GithubRepositoryLinkId(UUID id, BigDecimal gri) {
            this.projectId = id;
            this.githubRepoId = gri;
        }
    }
}
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
@Table(name = "github_project_link", schema = "api")
public class GithubProjectLink {
    @EmbeddedId
    private GithubProjectLinkId id;

    @MapsId("projectId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private DashboardProject project;

    public GithubProjectLink(UUID projectId, BigDecimal githubProjectId){
        id = new GithubProjectLinkId(projectId, githubProjectId);
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @Embeddable
    public static class GithubProjectLinkId implements Serializable {
        @Serial
        private static final long serialVersionUID = -8542208072100269995L;
        @Column(name = "project_id", nullable = false)
        private UUID projectId;

        @Column(name = "github_project_id", nullable = false)
        private BigDecimal githubProjectId;

        public GithubProjectLinkId(UUID projectId, BigDecimal githubProjectId) {
            this.projectId = projectId;
            this.githubProjectId = githubProjectId;
        }
    }
}
package com.zgamelogic.dashboard.database;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class ProjectGithubRepositoryLinkId {
    @Column(name = "projectid", nullable = false)
    private UUID projectid;

    @Column(name = "repoid", nullable = false)
    private BigDecimal repoid;
}
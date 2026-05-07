package com.zgamelogic.dashboard.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dashboard_projects")
public class DashboardProject {
    @Id
    @Column(nullable = false)
    private UUID id;
    private String description;
    private String name;


}
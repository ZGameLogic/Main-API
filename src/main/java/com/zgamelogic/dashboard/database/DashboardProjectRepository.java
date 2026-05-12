package com.zgamelogic.dashboard.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface DashboardProjectRepository extends JpaRepository<DashboardProject, UUID> {
    @Query("select distinct repoId from DashboardProject dp join dp.githubRepositoryLinks repoId")
    Set<Long> findAllGithubRepositoryLinks();

    @Query("select distinct applicationId from DashboardProject dp join dp.dataOtterProjectLinks applicationId")
    Set<Long> findAllDataOtterLinks();

    @Query("select distinct repoId from DashboardProject dp join dp.githubProjectLinks repoId")
    Set<Long> findAllGithubProjectLinks();
}

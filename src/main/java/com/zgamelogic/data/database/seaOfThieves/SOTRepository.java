package com.zgamelogic.data.database.seaOfThieves;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface SOTRepository extends JpaRepository<SOTDateAvailable, Long> {
}

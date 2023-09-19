package com.zgamelogic.data.database.ghosty.aspect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AspectRepository extends JpaRepository<Aspect, Integer> {

}
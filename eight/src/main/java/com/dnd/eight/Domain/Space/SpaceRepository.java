package com.dnd.eight.Domain.Space;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    List<Space> findByCode(String code);
}

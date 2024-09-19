package com.govtech.WeAreTheChampions.repository;

import com.govtech.WeAreTheChampions.entity.Team;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

    List<Team> findAllByGroupNumber(int groupNumber);
}

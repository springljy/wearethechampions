package com.govtech.WeAreTheChampions.repository;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.entity.Team;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.teamA.id = :teamId OR m.teamB.id = :teamId")
    List<Match> findByTeamId(Long teamId);
}

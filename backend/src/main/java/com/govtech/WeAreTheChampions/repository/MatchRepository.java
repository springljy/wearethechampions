package com.govtech.WeAreTheChampions.repository;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.entity.Team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTeam1OrTeam2(Team team1, Team team2);
}

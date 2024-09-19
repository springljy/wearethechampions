package com.govtech.WeAreTheChampions.repository;

import com.govtech.WeAreTheChampions.entity.Team;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

    List<Team> findAllByGroupNumber(int groupNumber);

    @Query(value = "SELECT * FROM team WHERE group_number = :groupNumber " +
                   "ORDER BY total_match_points DESC, " +
                   "total_goals_scored DESC, " +
                   "alternate_match_points DESC, " +
                   "STR_TO_DATE(registration_date, '%d/%m') ASC", 
           nativeQuery = true)
    List<Team> findRankedTeamsByGroupNumber(int groupNumber);
}

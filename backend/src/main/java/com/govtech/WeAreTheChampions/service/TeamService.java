package com.govtech.WeAreTheChampions.service;

import java.util.List;
import java.util.Optional;

import com.govtech.WeAreTheChampions.entity.Team;

public interface TeamService {
    Team addTeam(Team team);
    List<Team> getAllTeams();
    List<Team> getTeamsByGroup(int groupNumber);
    Optional<Team> getTeamByName(String name);
    void deleteAllTeams();
}
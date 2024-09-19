package com.govtech.WeAreTheChampions.service;

import java.util.List;
import java.util.Optional;

import com.govtech.WeAreTheChampions.entity.Team;

public interface TeamService {
    List<Team> addTeams(List<Team> teams);
    List<Team> getAllTeams();
    List<Team> getTeamsByGroup(int groupNumber);
    Optional<Team> getTeamByName(String name);
    void deleteAllTeams();
    Team updateTeam(Long id, Team team);
    boolean deleteTeam(Long id);
    List<Team> getRankedTeamsByGroupNumber(int groupNumber);
}
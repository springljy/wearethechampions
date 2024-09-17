package com.govtech.WeAreTheChampions.service.impl;

import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.repository.TeamRepository;
import com.govtech.WeAreTheChampions.service.TeamService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Team> getTeamsByGroup(int groupNumber) {
        return teamRepository.findAllByGroupNumber(groupNumber);
    }

    public Optional<Team> getTeamByName(String name) {
        return teamRepository.findByName(name);
    }

    public void deleteAllTeams() {
        teamRepository.deleteAll();
    }
}

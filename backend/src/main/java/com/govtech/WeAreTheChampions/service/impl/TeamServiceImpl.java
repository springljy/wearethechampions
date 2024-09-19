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

    @Override
    public List<Team> addTeams(List<Team> teams) {
        return teamRepository.saveAll(teams);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public List<Team> getTeamsByGroup(int groupNumber) {
        return teamRepository.findAllByGroupNumber(groupNumber);
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public void deleteAllTeams() {
        teamRepository.deleteAll();
    }

    @Override
    public Team updateTeam(Long id, Team team) {
        Optional<Team> existingTeamOpt = teamRepository.findById(id);
        Team existingTeam = existingTeamOpt.get();
        existingTeam.setName(team.getName());
        existingTeam.setRegistrationDate(team.getRegistrationDate());
        existingTeam.setGroupNumber(team.getGroupNumber());
        return teamRepository.save(existingTeam);
    }

    @Override
    public boolean deleteTeam(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            teamRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Team> getRankedTeamsByGroupNumber(int groupNumber) {
        return teamRepository.findRankedTeamsByGroupNumber(groupNumber);
    }
}

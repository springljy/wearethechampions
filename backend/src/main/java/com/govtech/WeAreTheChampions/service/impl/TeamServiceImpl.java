package com.govtech.WeAreTheChampions.service.impl;

import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.entity.AuditLog;
import com.govtech.WeAreTheChampions.repository.TeamRepository;
import com.govtech.WeAreTheChampions.service.AuditLogService;
import com.govtech.WeAreTheChampions.service.TeamService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private AuditLogService auditLogService;

    @Override
    public List<Team> addTeams(List<Team> teams) {
        List<Team> savedTeams = teamRepository.saveAll(teams);
        for (Team team : savedTeams) {
            auditLogService.logAction(1L, "CREATE", "Team", team.getId(), "Created team with name: " + team.getName());
        }
        return savedTeams;
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
        auditLogService.logAction(1L, "DELETE", "Team", null, "Deleted all teams.");
    }

    @Override
    public Team updateTeam(Long id, Team team) {
        Optional<Team> existingTeamOpt = teamRepository.findById(id);
        Team existingTeam = existingTeamOpt.get();
        existingTeam.setName(team.getName());
        existingTeam.setRegistrationDate(team.getRegistrationDate());
        existingTeam.setGroupNumber(team.getGroupNumber());
        Team updatedTeam = teamRepository.save(existingTeam);

        auditLogService.logAction(1L, "UPDATE", "Team", updatedTeam.getId(), "Updated team with name: " + updatedTeam.getName());
        return updatedTeam;
    }

    @Override
    public boolean deleteTeam(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            teamRepository.deleteById(id);
            auditLogService.logAction(1L, "DELETE", "Team", id, "Deleted team with name: " + team.get().getName());
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

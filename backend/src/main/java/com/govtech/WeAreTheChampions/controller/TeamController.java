package com.govtech.WeAreTheChampions.controller;

import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.service.TeamService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamController {

    private TeamService teamService;

    @PostMapping
    public ResponseEntity<List<Team>> addTeams(@RequestBody List<Team> teams) {
        List<Team> savedTeams = teamService.addTeams(teams);
        return ResponseEntity.ok(savedTeams);
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(id, team);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearTeams() {
        teamService.deleteAllTeams();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        boolean isDeleted = teamService.deleteTeam(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rankings")
    public ResponseEntity<List<Team>> getRankedTeams(@RequestParam int groupNumber) {
        List<Team> rankedTeams = teamService.getRankedTeamsByGroupNumber(groupNumber);
        return ResponseEntity.ok(rankedTeams);
    }
}

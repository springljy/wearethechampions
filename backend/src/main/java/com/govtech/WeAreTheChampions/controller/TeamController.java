package com.govtech.WeAreTheChampions.controller;

import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.service.TeamService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        Team savedTeam = teamService.addTeam(team);
        return ResponseEntity.ok(savedTeam);
    }

    @GetMapping("/group/{groupNumber}")
    public ResponseEntity<List<Team>> getTeamsByGroup(@PathVariable int groupNumber) {
        List<Team> teams = teamService.getTeamsByGroup(groupNumber);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Team> getTeamByName(@PathVariable String name) {
        Optional<Team> team = teamService.getTeamByName(name);
        return team.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> clearTeams() {
        teamService.deleteAllTeams();
        return ResponseEntity.noContent().build();
    }
}

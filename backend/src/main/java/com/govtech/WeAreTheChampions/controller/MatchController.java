package com.govtech.WeAreTheChampions.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.service.MatchService;
import com.govtech.WeAreTheChampions.service.TeamService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private MatchService matchService;
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Match> addMatch(@RequestBody Match match) {
        Match savedMatch = matchService.addMatch(match);
        return ResponseEntity.ok(savedMatch);
    }

    @GetMapping("/team/{teamName}")
    public ResponseEntity<List<Match>> getMatchesByTeam(@PathVariable String teamName) {
        Optional<Team> team = teamService.getTeamByName(teamName);
        if (team.isPresent()) {
            List<Match> matches = matchService.getMatchesByTeam(team.get());
            return ResponseEntity.ok(matches);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> clearMatches() {
        matchService.deleteAllMatches();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rankings")
    public ResponseEntity<Void> calculateRankings() {
        matchService.calculateTeamPointsAndRankings();
        return ResponseEntity.ok().build();
    }
}


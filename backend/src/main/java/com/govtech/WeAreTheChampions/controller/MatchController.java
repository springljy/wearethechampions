package com.govtech.WeAreTheChampions.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.dto.MatchDto;
import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.service.MatchService;
import com.govtech.WeAreTheChampions.service.TeamService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
@AllArgsConstructor
public class MatchController {

    private MatchService matchService;
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<?> addMatches(@RequestBody List<MatchDto> matchDtos) {
        List<Match> newMatches = matchService.addMatches(matchDtos);

        for (MatchDto matchDto : matchDtos) {
            System.out.println("MatchDto - Team A Goals: " + matchDto.getTeamAGoals() + ", Team B Goals: " + matchDto.getTeamBGoals());
        }

        List<MatchDto> response = newMatches.stream()
                                            .map(MatchDto::fromEntity)
                                            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearMatches() {
        matchService.deleteAllMatches();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        boolean isDeleted = matchService.deleteMatch(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rankings")
    public ResponseEntity<Void> calculateRankings() {
        matchService.calculateTeamPointsAndRankings();
        return ResponseEntity.ok().build();
    }
}


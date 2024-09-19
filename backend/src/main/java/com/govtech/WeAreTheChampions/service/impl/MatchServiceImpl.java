package com.govtech.WeAreTheChampions.service.impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.dto.MatchDto;
import com.govtech.WeAreTheChampions.repository.MatchRepository;
import com.govtech.WeAreTheChampions.repository.TeamRepository;
import com.govtech.WeAreTheChampions.service.MatchService;
import com.govtech.WeAreTheChampions.service.AuditLogService;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

    private MatchRepository matchRepository;
    private TeamRepository teamRepository;

    private AuditLogService auditLogService;

    @Override
    public List<Match> addMatches(List<MatchDto> matches) {

        List<Match> newMatches = new ArrayList<>();

        for (int i = 0; i < matches.size(); i++) {

            MatchDto matchDto = matches.get(i);
            Team teamA = teamRepository.findByName(matchDto.getTeamAName())
                    .orElseThrow(() -> new RuntimeException("Team A not found"));
            Team teamB = teamRepository.findByName(matchDto.getTeamBName())
                    .orElseThrow(() -> new RuntimeException("Team B not found"));
            int teamAGoals = matchDto.getTeamAGoals();
            int teamBGoals = matchDto.getTeamBGoals();

            Match match = Match.builder()
                    .teamA(teamA)
                    .teamB(teamB)
                    .teamAGoals(teamAGoals)
                    .teamBGoals(teamBGoals)
                    .result(calculateResult(teamAGoals, teamBGoals))
                    .build();

            newMatches.add(match);
            updateTeamStats(teamA, teamB, teamAGoals, teamBGoals);

            auditLogService.logAction(1L, "CREATE", "Match", match.getId(),
                    "Created match between " + teamA.getName() + " and " + teamB.getName() +
                            " with result: " + match.getResult());
        }

        return matchRepository.saveAll(newMatches);
    }

    private void updateTeamStats(Team teamA, Team teamB, int teamAGoals, int teamBGoals) {
        if (teamAGoals > teamBGoals) {
            teamA.setTotalMatchPoints(teamA.getTotalMatchPoints() + 3);
            teamA.setAlternateMatchPoints(teamA.getAlternateMatchPoints() + 5);
        } else if (teamAGoals < teamBGoals) {
            teamB.setTotalMatchPoints(teamB.getTotalMatchPoints() + 3);
            teamB.setAlternateMatchPoints(teamB.getAlternateMatchPoints() + 5);
        } else {
            teamA.setTotalMatchPoints(teamA.getTotalMatchPoints() + 1);
            teamB.setTotalMatchPoints(teamB.getTotalMatchPoints() + 1);
            teamA.setAlternateMatchPoints(teamA.getAlternateMatchPoints() + 3);
            teamB.setAlternateMatchPoints(teamB.getAlternateMatchPoints() + 3);
        }

        teamA.setTotalGoalsScored(teamA.getTotalGoalsScored() + teamAGoals);
        teamB.setTotalGoalsScored(teamB.getTotalGoalsScored() + teamBGoals);

        teamA.setMatchesPlayed(teamA.getMatchesPlayed() + 1);
        teamB.setMatchesPlayed(teamB.getMatchesPlayed() + 1);

        teamRepository.save(teamA);
        teamRepository.save(teamB);
    }

    private String calculateResult(int teamAGoals, int teamBGoals) {
        if (teamAGoals > teamBGoals) {
            return "win";
        } else if (teamAGoals < teamBGoals) {
            return "loss";
        } else {
            return "draw";
        }
    }

    public void deleteAllMatches() {
        matchRepository.deleteAll();
        auditLogService.logAction(1L, "DELETE", "Match", null, "Deleted all matches.");
    }

    @Override
    public boolean deleteMatch(Long id) {
        Optional<Match> match = matchRepository.findById(id);
        if (match.isPresent()) {
            matchRepository.deleteById(id);
            auditLogService.logAction(1L, "DELETE", "Match", id,
                    "Deleted match with ID: " + id);
            return true;
        } else {
            return false;
        }
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public List<Match> getMatchesByTeam(Long teamId) {
        return matchRepository.findByTeamId(teamId);
    }
}

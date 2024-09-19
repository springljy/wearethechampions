package com.govtech.WeAreTheChampions.service.impl;

import org.springframework.stereotype.Service;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.repository.MatchRepository;
import com.govtech.WeAreTheChampions.service.MatchService;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepository matchRepository;

    public Match addMatch(Match match) {
        return matchRepository.save(match);
    }

    public List<Match> getMatchesByTeam(Team team) {
        return matchRepository.findByTeam1OrTeam2(team, team);
    }

    public void deleteAllMatches() {
        matchRepository.deleteAll();
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public void calculateTeamPointsAndRankings() {
    }
}

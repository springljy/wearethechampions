package com.govtech.WeAreTheChampions.service;

import java.util.List;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.entity.Team;

public interface MatchService {

    Match addMatch(Match match);
    List<Match> getMatchesByTeam(Team team);
    void deleteAllMatches();
    List<Match> getAllMatches();
    void calculateTeamPointsAndRankings();
    
}

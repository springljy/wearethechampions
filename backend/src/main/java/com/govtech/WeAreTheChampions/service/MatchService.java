package com.govtech.WeAreTheChampions.service;

import java.util.List;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.dto.MatchDto;
import com.govtech.WeAreTheChampions.entity.Team;

public interface MatchService {

    List<Match> addMatches(List<MatchDto> matches);
    void deleteAllMatches();
    boolean deleteMatch(Long id);
    List<Match> getAllMatches();
    List<Match> getMatchesByTeam(Long teamId);
}

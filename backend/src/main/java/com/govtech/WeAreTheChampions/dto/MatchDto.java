package com.govtech.WeAreTheChampions.dto;

import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.entity.Match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
    private String teamAName;
    private String teamBName;
    private int teamAGoals;
    private int teamBGoals;

    public Match toEntity(Team teamA, Team teamB) {
        return Match.builder()
                .teamA(teamA)
                .teamB(teamB)
                .teamAGoals(this.teamAGoals)
                .teamBGoals(this.teamBGoals)
                .build();
    }

    public static MatchDto fromEntity(Match match) {
        return MatchDto.builder()
                .teamAName(match.getTeamA().getName())
                .teamBName(match.getTeamB().getName())
                .teamAGoals(match.getTeamAGoals())
                .teamBGoals(match.getTeamBGoals())
                .build();
    }

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public int getTeamAGoals() {
        return teamAGoals;
    }

    public void setTeamAGoals(int teamAGoals) {
        this.teamAGoals = teamAGoals;
    }

    public int getTeamBGoals() {
        return teamBGoals;
    }

    public void setTeamBGoals(int teamBGoals) {
        this.teamBGoals = teamBGoals;
    }
    
}

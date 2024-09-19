package com.govtech.WeAreTheChampions.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.Set;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "registration_date", nullable = false)
    private String registrationDate;

    @Column(name = "group_number", nullable = false)
    private int groupNumber;

    @Builder.Default
    @Column(name = "total_match_points", nullable = false)
    private int totalMatchPoints = 0;  // 3 for win, 1 for draw, 0 for loss

    @Builder.Default
    @Column(name = "total_goals_scored", nullable = false)
    private int totalGoalsScored = 0;

    @Builder.Default
    @Column(name = "alternate_match_points", nullable = false)
    private int alternateMatchPoints = 0;  // 5 for win, 3 for draw, 1 for loss

    @Builder.Default
    @Column(name = "matches_played", nullable = false)
    private int matchesPlayed = 0;
}
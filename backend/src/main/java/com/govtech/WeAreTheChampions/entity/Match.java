package com.govtech.WeAreTheChampions.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_a_goals", nullable = false)
    private int teamAGoals;

    @Column(name = "team_b_goals", nullable = false)
    private int teamBGoals;

    @ManyToOne
    @JoinColumn(name = "team_a_id", nullable = false)
    private Team teamA;

    @ManyToOne
    @JoinColumn(name = "team_b_id", nullable = false)
    private Team teamB;

    @Column(name = "result", nullable = false)
    private String result; // 'win', 'draw', or 'loss'
    
}
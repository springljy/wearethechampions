package com.govtech.WeAreTheChampions.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "match")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    private Team team2;

    @Column(name = "team1_goals", nullable = false)
    private int team1Goals;

    @Column(name = "team2_goals", nullable = false)
    private int team2Goals;
    
}
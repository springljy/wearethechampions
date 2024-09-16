package com.govtech.WeAreTheChampions.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate registrationDate;

    @Column(name = "group_number", nullable = false)
    private int groupNumber;
    
}

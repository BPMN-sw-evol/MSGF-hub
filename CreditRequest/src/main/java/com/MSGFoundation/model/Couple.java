package com.MSGFoundation.model;

import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "COUPLE")
@Data
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codCouple")
    private Long id;
    @OneToOne
    @JoinColumn(name = "partner1Id")
    private Person partner1;
    @OneToOne
    @JoinColumn(name = "partner2Id")
    private Person partner2;
}

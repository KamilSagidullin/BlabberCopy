package com.example.blabbercopy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "follower_id",nullable = false)
    private User follower;
    @ManyToOne
    @JoinColumn(name = "followee_id",nullable = false)
    private User followee;
}

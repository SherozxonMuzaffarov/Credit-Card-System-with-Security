package com.practice.practicejwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Long id;
    private String username;
    private long number;
    private double balance;
    private Date expiredDate;
    private boolean active;
}

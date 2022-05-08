package com.practice.practicejwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Outcome {

    private Long id;
    private String from_card_id;
    private String to_card_id;
    private double amount;
    private Date date;
    private double commissionAmount;
}

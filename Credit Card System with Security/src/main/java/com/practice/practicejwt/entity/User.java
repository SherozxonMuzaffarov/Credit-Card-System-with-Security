package com.practice.practicejwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;
    private double currentAmount;
    private String cardId;
    private String cardPassword;
    private List<Outcome> outcomes;
    private List<Income> incomes;

}

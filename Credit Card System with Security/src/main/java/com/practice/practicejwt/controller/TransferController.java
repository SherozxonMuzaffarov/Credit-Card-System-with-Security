package com.practice.practicejwt.controller;

import com.practice.practicejwt.entity.Income;
import com.practice.practicejwt.entity.Outcome;
import com.practice.practicejwt.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    List<User> userList = new ArrayList<>(
            Arrays.asList(
                    new User("admin1",50_000,"12345","password",new ArrayList<>(),new ArrayList<>()),
                    new User("admin2",100_000,"123456","password",new ArrayList<>(),new ArrayList<>()),
                    new User("admin3",10_000_000,"123457","password",new ArrayList<>(),new ArrayList<>())
            )
    );

    @PostMapping("/send")
    private HttpEntity<?> makeTransfer(@RequestBody Outcome outcome){
        if (outcome.getCommissionAmount()>1000)
            return ResponseEntity.status(401).body("sorry");

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        if (outcome.getFrom_card_id().equals(outcome.getTo_card_id()))
            return ResponseEntity.status(401).body("You can't send to your card");

        User currentClient = null;int clientId=0;
        User currentUser = null;int userId=0;


        //Set user and client to object by card id
        for (int i=0;i<userList.size();i++) {
            if(userList.get(i).getCardId().equals(outcome.getTo_card_id())) {currentClient=userList.get(i);clientId=i;}
            if(userList.get(i).getCardId().equals(outcome.getFrom_card_id())) {currentUser=userList.get(i);userId=i;}
        }

        //Check User and Client card id to exist
        if (currentUser != null) {
            if (!currentUser.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                return ResponseEntity.status(401).body("Card is not yours! Enter your card id");
            if (currentClient!= null) {

                if (currentUser.getCurrentAmount() > outcome.getAmount()) {

                    outcome.setDate(new Date());
                    userList.get(userId).getOutcomes().add(outcome);
                    userList.get(userId).setCurrentAmount(userList.get(userId).getCurrentAmount()-outcome.getAmount());

                    Income income=new Income();
                    income.setDate(new Date());
                    income.setFrom_card_id(userList.get(userId).getCardId());
                    income.setAmount(outcome.getAmount());
                    userList.get(clientId).setCurrentAmount((long) (userList.get(clientId).getCurrentAmount()+outcome.getAmount()));
                    userList.get(clientId).getIncomes().add(income);

                    System.out.println(userList.get(clientId));
                    System.out.println(userList.get(userId));
                    return ResponseEntity.ok("Successfully transacted");
                } else
                    return   ResponseEntity.status(401).body("Your balance is not enough to accomplish transaction! Replenish your account");

            } else ResponseEntity.status(404).body("Please Enter valid card id");
        } else ResponseEntity.status(404).body("Please Enter valid card id");


        return ResponseEntity.ok("Send method not work");
    }

    @GetMapping("/outcomes")
    public ResponseEntity<?> getOutcomes(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        for (User user : userList) {
            if (user.getUsername().equals(username)){
                return ResponseEntity.ok(user.getOutcomes());
            }
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/incomes")
    public ResponseEntity<?> getIncomes(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        for (User user : userList) {
            if (user.getUsername().equals(username)){
                return ResponseEntity.ok(user.getIncomes());
            }
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        for (User user : userList) {
            if (user.getUsername().equals(username)){
                return ResponseEntity.ok(user.getCurrentAmount());
            }
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping
    public ResponseEntity<?> mainPage() {
        return ResponseEntity.ok("Welcome MyPay app \n\n Note !!! \n  .Cash transaction can't be more than 100 000 sum \n .Enter exist card id \n .Check your balance before transaction");

    }
}

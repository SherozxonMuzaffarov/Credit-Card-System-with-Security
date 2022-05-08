package com.practice.practicejwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userList = new ArrayList<>(Arrays.asList(
                new User("admin1", passwordEncoder.encode("password1"), new ArrayList<>()),
                new User("admin2", passwordEncoder.encode("password2"), new ArrayList<>())
        ));

        for (User user:userList){
            boolean exists = username.equals(user.getUsername());
            if (exists)
                return user;
        }
        throw new UsernameNotFoundException("username not found");
    }
}

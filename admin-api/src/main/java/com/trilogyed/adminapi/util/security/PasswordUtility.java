package com.trilogyed.adminapi.util.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PasswordUtility {
    public static List<String> createPassword(String ... passwords) {
        PasswordEncoder enc = new BCryptPasswordEncoder();
        List<String> passList = new ArrayList<>();
        Arrays.stream(passwords).forEach(p->
            {
                String temp = enc.encode(p);
                passList.add(temp);
                System.out.println(temp);
            }
        );
        return passList;
    }

    public static void main(String[] args) {
        List<String> passwords = createPassword("password");
    }
}

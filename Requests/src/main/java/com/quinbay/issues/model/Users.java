package com.quinbay.issues.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    String userId;
    String userName;
    String password;
    String role;
    String phone;
    String email;
    String repManagerId;
}

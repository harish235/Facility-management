package com.quinbay.scheduler.model;

import lombok.Data;

import javax.persistence.Id;
@Data
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

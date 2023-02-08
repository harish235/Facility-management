package com.quinbay.users.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Userresponse {
    Integer userId;
    String username;
    String userEmail;
    String userRole;
    String phoneNumber;
    String reporterName;

    public Userresponse(Integer userId, String username, String userEmail,  String phoneNumber, String reporterName) {
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.reporterName = reporterName;
    }
}
package com.quinbay.scheduler.api;

import com.quinbay.scheduler.model.EmailDetails;
import com.quinbay.scheduler.model.ReturnData;
import com.quinbay.scheduler.model.Users;

import java.util.List;
import java.util.Optional;


public interface MailAPI {
//    String sendSimpleMail(EmailDetails details);
    List<Users> displayAllUsers();
    ReturnData displayByUserId(String id);
}

package com.quinbay.scheduler.controller;

import com.quinbay.scheduler.api.MailAPI;
import com.quinbay.scheduler.model.EmailDetails;
import com.quinbay.scheduler.model.ReturnData;
import com.quinbay.scheduler.model.Users;
import com.quinbay.scheduler.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    MailAPI mailAPI;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details)
    {
//        String status = mailService.sendSimpleMail(details);
        return null;
    }

    @GetMapping("/viewAllUsers")
    public Object displayAllUsers() {
        return mailAPI.displayAllUsers();
    }

    @GetMapping("/viewByUserId")
    public ReturnData displayByUserId(@RequestParam String id) {
        return mailAPI.displayByUserId(id);
    }
}

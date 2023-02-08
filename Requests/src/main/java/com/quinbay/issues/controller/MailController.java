//package com.quinbay.issues.controller;
//
//import com.quinbay.issues.service.MailingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/mail")
//public class MailController {
//
//    @Autowired
//    MailingService mailingService;
//
////    @GetMapping("/getAssigneeDetails")
////    public String getAssigneeDetails(@RequestParam String userId) {
////        return mailingService.getAssigneeDetails(userId);
////    }
//
//    @GetMapping("/mailForOverdue")
//    public String mailForOverdue(@RequestParam int issueId, @RequestParam  int userId) {
//        return mailingService.mailForOverdue(issueId, userId);
//    }
//
//    @GetMapping("/mailForCloseRequest")
//    public String mailForCloseRequest(@RequestParam int issueId){
//        return mailingService.mailForCloseRequest(issueId);
//    }
//}

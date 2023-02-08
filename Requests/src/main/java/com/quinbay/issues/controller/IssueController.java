package com.quinbay.issues.controller;

import com.quinbay.issues.Pojo.ChatDetails;
import com.quinbay.issues.Pojo.IssueResponse;
import com.quinbay.issues.model.StatusType;
import com.quinbay.issues.service.IssuesService;
import com.quinbay.issues.model.IssueHistory;
import com.quinbay.issues.model.Issues;
import com.quinbay.issues.Pojo.IssuesInput;
import com.quinbay.issues.Pojo.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    IssuesService issuesService;

    @PostMapping("/add")
    public ReturnData addIssue(@RequestBody IssuesInput issuesInput) {
        return issuesService.addIssue(issuesInput);
    }

    @GetMapping("/viewIssuesByAssigneeId/{assigneeUserId}")
    public List<Issues> viewIssuesByAssigneeId(@PathVariable int assigneeUserId) {
        return issuesService.viewIssuesByAssigneeId(assigneeUserId);
    }

    @PutMapping("/updateStatus")
    public ReturnData updateStatus(@RequestParam int issueId, @RequestParam StatusType status) {
        return issuesService.updateStatus(issueId, status);
    }

    @PutMapping("/reopenIssue")
    public ReturnData reopenIssue(@RequestParam int userId, @RequestParam int issueId){
        return issuesService.reopenIssue(userId, issueId);
    }

    @GetMapping("/viewAll")
    public ReturnData viewIssues() {
        return issuesService.viewIssues();
    }

    @GetMapping("/viewIssuesByRole")
    public ReturnData viewIssuesByRole(@RequestParam int userid, @RequestParam String role) {
        return issuesService.viewIssuesByRole(userid, role);
    }

    @PostMapping("/close")
    public ReturnData closeIssue(int issueId) {
        return issuesService.closeIssue(issueId);
    }

//    @GetMapping("/viewClosedIssues")
//    public List<Issues> viewClosedIssues() {
//        return issuesService.viewClosedIssues();
//    }

    @PutMapping("/addComment")
    public ReturnData addComments(@RequestParam int issueId, @RequestParam String comment) {
        return issuesService.addComments(issueId, comment);
    }

    @GetMapping("/getCount")
    public HashMap<String, Integer> countOfStatus() {
        return issuesService.countOfStatus();
    }

    @DeleteMapping("/deleteIssue")
    public ReturnData deleteIssue(@RequestParam int issueId) {
        return issuesService.deleteIssue(issueId);
    }

    @GetMapping("/getCategories")
    public List<String> getCategories() {
        return issuesService.getCategories();
    }

    @GetMapping("/viewByStatus")
    public List<IssueResponse> viewByStatus(@RequestParam StatusType status) {
        return issuesService.viewByStatus(status);
    }

    @GetMapping("/viewByCategories")
    public List<Issues> viewByCategories(@RequestParam String category) {
        return issuesService.viewByCategories(category);
    }

    @PostMapping("/addCategory")
    public ReturnData addCategory(@RequestParam String category, @RequestParam int validityTime) {
        return issuesService.addCategory(category, validityTime);
    }

    @GetMapping("/viewByPriority")
    public List<Issues> viewByPriority(@RequestParam String priority) {
        return issuesService.viewByPriority(priority);
    }

    @GetMapping("/checkForOverdues")
    public List<Issues> checkForOverdues() {
        return issuesService.checkForOverdues();
    }

    @GetMapping("/checkForIncomplete")
    public ReturnData checkForIncomplete() {
        return issuesService.checkForIncomplete();
    }

    @GetMapping("/viewIssueHistory")
    public List<IssueHistory> viewIssueHistory(@RequestParam int issueId){
        return issuesService.viewIssueHistory(issueId);
    }

    @GetMapping("/displayByPages")
    public ResponseEntity<Map<String, Object>> displayByPages(@RequestParam int pgNo, @RequestParam int pgSize, @RequestParam int userId, @RequestParam String role){
        return issuesService.displayByPages(pgNo, pgSize, userId, role);
    }

    @PostMapping("/addChat")
    public ResponseEntity addChat(@RequestBody ChatDetails chatDetails){
        return issuesService.addChat(chatDetails);
    }

    @GetMapping("/getChat")
    public ReturnData getChat(@RequestParam Integer id){
        return issuesService.getChat(id);
    }
}

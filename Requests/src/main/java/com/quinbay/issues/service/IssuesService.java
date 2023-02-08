package com.quinbay.issues.service;

import com.quinbay.issues.Pojo.ChatDetails;
import com.quinbay.issues.Pojo.IssueResponse;
import com.quinbay.issues.model.IssueHistory;
import com.quinbay.issues.model.Issues;
import com.quinbay.issues.Pojo.IssuesInput;
import com.quinbay.issues.Pojo.ReturnData;
import com.quinbay.issues.model.StatusType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/issue")
public interface IssuesService {
    ReturnData addIssue(IssuesInput issuesInput);
    List<Issues> viewIssuesByAssigneeId(int assigneeUserId);
//    ReturnData updateStatus(int issueId, String status);
    ReturnData updateStatus(int issueId, StatusType status);
    ReturnData viewIssues();
    ReturnData closeIssue(int issueId);
//    List<Issues> viewClosedIssues();
    ReturnData addComments(int issueId, String comment);
    HashMap<String, Integer> countOfStatus();
    ReturnData deleteIssue(int issueId);
    List<String> getCategories();
    List<IssueResponse> viewByStatus(StatusType status);
    List<Issues> viewByCategories(String category);
    ReturnData addCategory(String category, int validity);
    List<Issues> viewByPriority(String priority);
//    ReturnData checkForOverdues();
    List<Issues> checkForOverdues();
    ReturnData checkForIncomplete();
//    List<Issues> displayByPages(int pageNo, int pageSize);
    ReturnData reopenIssue(int userId, int issueId);
    ReturnData viewIssuesByRole(int id, String role);
    List<IssueHistory> viewIssueHistory(int id);
    ResponseEntity<Map<String, Object>> displayByPages(int pageNo, int pageSize, int id, String role);
    ResponseEntity addChat(ChatDetails chatDetails);
    ReturnData getChat(Integer issueId);
}
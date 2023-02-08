package com.quinbay.issues.Pojo;

//import com.quinbay.issues.model.ClosedIssues;
import com.quinbay.issues.model.Chat;
import com.quinbay.issues.model.Issues;
import com.quinbay.issues.model.Users;
import lombok.Data;

import java.util.List;

@Data
public class ReturnData {
    boolean status;
    String message;
    List<Issues> issues;
    List<ChatResponse> chatList;
    Users userData;
    int count;
//    List<ClosedIssues> closedIssues;
}

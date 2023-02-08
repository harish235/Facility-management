package com.quinbay.issues.Pojo;

import lombok.Data;

@Data
public class IssuesInput {
    String assigneeUserId;
    String type;
    String category;
    String sla;
    String description;
}

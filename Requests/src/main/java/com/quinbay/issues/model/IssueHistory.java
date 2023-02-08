package com.quinbay.issues.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IssueHistory {
    @Id
    @SequenceGenerator(name = "history_seq", sequenceName = "history_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_seq")
    Integer id;
    Integer issueId;
    Integer assigneeUserId;  // assigned employee
    String issueType;   //request/grievance
    String issueCategory;  //washroom or stationaries
    String sla;  //time duration within which it should be resolved
    String description;
    String priority;   // based on sla
//    String status; //completed, reviewed, overdue etc...
    StatusType status;
    String comments;  //comments posted by admin
    Date createdDate;
    Date deadlineDate;
    Date updatedDate;

    public IssueHistory(Integer assigneeUserId, String issueType, String issueCategory, String sla, String description) {
        this.assigneeUserId = assigneeUserId;
        this.issueType = issueType;
        this.issueCategory = issueCategory;
        this.sla = sla;
        this.description = description;
    }

    public IssueHistory(Integer issueId, Integer assigneeUserId, String issueType, String issueCategory, String sla, String description, String priority, StatusType status, Date createdDate, Date deadlineDate, Date updatedDate) {
        this.issueId = issueId;
        this.assigneeUserId = assigneeUserId;
        this.issueType = issueType;
        this.issueCategory = issueCategory;
        this.sla = sla;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdDate = createdDate;
        this.deadlineDate = deadlineDate;
        this.updatedDate = updatedDate;
    }
}

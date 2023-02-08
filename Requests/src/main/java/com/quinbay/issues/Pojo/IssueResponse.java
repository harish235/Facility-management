package com.quinbay.issues.Pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quinbay.issues.model.IssueHistory;
import com.quinbay.issues.model.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {
    @Id
    Integer id;
    Integer assigneeUserId;  // assigned employee
    String issueType;   //request/grievance
    String issueCategory;  //washroom or stationaries
    String sla;  //time duration within which it should be resolved
    String description;
    String priority;   // based on sla
    StatusType status; //completed, reviewed, overdue etc...
    String comments;  //comments posted by admin
    Date createdDate;
    Date deadlineDate;
    Date updatedDate;

    @JsonIgnore
    List<IssueHistory> issuehistory;

    public IssueResponse(Integer id, Integer assigneeUserId, String issueType, String issueCategory, String sla, String description, String priority, StatusType status, Date createdDate, Date deadlineDate, Date updatedDate) {
        this.id = id;
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

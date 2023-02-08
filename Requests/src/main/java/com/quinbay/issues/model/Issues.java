package com.quinbay.issues.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Issues {

    public enum Status {
        OPEN, REVIEWED, OVERDUE, COMPLETED, CLOSED
    }

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "my_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    Integer id;
    Integer assigneeUserId;  // assigned employee
    String issueType;   //request/grievance
    String issueCategory;  //washroom or stationaries
    String sla;  //time duration within which it should be resolved
    String description;
    String priority;   // based on sla
//    String status; //completed, reviewed, overdue etc...
    StatusType status;
//    String comments;  //comments posted by admin
    Date createdDate;
    Date deadlineDate;
    Date updatedDate;  // based on status change date

    @JsonIgnore
    @OneToMany(targetEntity = IssueHistory.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ih_fk", referencedColumnName = "id")
    List<IssueHistory> issuehistory;

    @JsonIgnore
    @OneToMany(targetEntity = Chat.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_fk", referencedColumnName = "id")
    List<Chat> chatList;

    public Issues(Integer assigneeUserId, String issueType, String issueCategory, String sla, String description) {
        this.assigneeUserId = assigneeUserId;
        this.issueType = issueType;
        this.issueCategory = issueCategory;
        this.sla = sla;
        this.description = description;
    }

}

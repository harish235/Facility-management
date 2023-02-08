package com.quinbay.scheduler.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Issues {
    @Id
    Integer id;
    String issueId;
    String type;
    String category;
    String sla;
    String description;
    String priority;
    String status;
    String comments;
    String assigneeUserId;
    Date createdDate;
    Date deadlineDate;
    Date reviewedDate;
    Date completedDate;
    Date closedDate;
}

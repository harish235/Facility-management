package com.quinbay.scheduler.model;

import lombok.Data;

@Data
public class ReturnData {
    boolean status;
    String message;
    Users userData;
    Issues issueData;
}

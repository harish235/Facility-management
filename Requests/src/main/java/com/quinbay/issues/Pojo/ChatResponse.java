package com.quinbay.issues.Pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
public class ChatResponse {
    Integer issueId;
    String msgType;
    String text;
    Integer senderId;
    Date timestamp;
//    String senderName;
//    String senderRole;

    public ChatResponse(Integer issueId, String msgType, String text, Integer senderId, Date timestamp) {
        this.issueId = issueId;
        this.msgType = msgType;
        this.text = text;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }
}

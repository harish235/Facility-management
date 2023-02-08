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
public class Chat {
    @Id
    @SequenceGenerator(name = "chat_seq", sequenceName = "chat_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    Integer id;
    Integer issueId;
    String msgType;
    String text;
    Integer senderId;
    Date timestamp;

    public Chat(Integer issueId, String text, Integer senderId) {
        this.issueId = issueId;
        this.text = text;
        this.senderId = senderId;
    }
}

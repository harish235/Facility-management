package com.quinbay.issues.Pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDetails {
    Integer issueId;
    Integer senderId;
    String text;
}

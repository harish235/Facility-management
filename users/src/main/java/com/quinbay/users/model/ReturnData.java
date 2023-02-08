package com.quinbay.users.model;

import com.quinbay.users.pojo.Userresponse;
import lombok.Data;

import java.util.Optional;

@Data
public class ReturnData {
    boolean status;
    String message;
    Userresponse userData;
}
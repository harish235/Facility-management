package com.quinbay.users.api;

import com.quinbay.users.model.ReturnData;
import com.quinbay.users.model.Users;
import com.quinbay.users.pojo.UserRequest;

import java.util.List;

public interface AuthAPI {
    ReturnData authUser(String email, String pwd);
    List<Users> displayAllUsers();
    ReturnData displayByUserId(String id);
    ReturnData login(UserRequest userRequest);
//    ReturnData addManager(Manager roledata);
//    List<Users> displayByPages(int pageNo, int pageSize);
}
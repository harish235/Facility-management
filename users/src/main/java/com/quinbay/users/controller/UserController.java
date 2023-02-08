package com.quinbay.users.controller;

import com.quinbay.users.api.AuthAPI;
import com.quinbay.users.model.ReturnData;
import com.quinbay.users.model.Users;
import com.quinbay.users.pojo.UserRequest;
import com.quinbay.users.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthAPI authAPI;

    @GetMapping("/check")
    public String sendmessage() {
        return "Connected";
    }

    @GetMapping("/viewAll")
    public List<Users> displayAllUsers() {
        System.out.println("Inside disp all user controller");
        return authService.displayAllUsers();
    }

    @GetMapping("/viewByUserId")
    public ReturnData displayByUserId(@RequestParam String userId) {
        System.out.println("Inside disp by user id controller");
        return authService.displayByUserId(userId);
    }

    @PostMapping("/authUser")
    public ReturnData authUser(@RequestParam String email, @RequestParam String password) {
        return authService.authUser(email, password);
    }

    @PostMapping("/login")
    public ReturnData login(@RequestBody UserRequest userRequest){
        return authService.login(userRequest);
    }

//    @PostMapping("/addUser")
//    public ReturnData addRole(@RequestBody Manager data){
//        return authAPI.addManager(data);
//    }

//    @GetMapping("/details/{pageNo}")
//    public List<Users> displayByPages(@PathVariable int pageNo) {
//        int pageSize = 5;
//        return authService.displayByPages(pageNo, pageSize);
//    }
}
package com.quinbay.users.service;

import com.quinbay.users.api.AuthAPI;
import com.quinbay.users.model.ReturnData;
import com.quinbay.users.model.Role;
import com.quinbay.users.model.Users;
import com.quinbay.users.pojo.UserRequest;
import com.quinbay.users.pojo.Userresponse;
import com.quinbay.users.repository.RoleRepository;
import com.quinbay.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements AuthAPI {

    @Autowired UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    @Override
    public ReturnData authUser(String email, String pwd) {
        ReturnData returnData = new ReturnData();
        try {
            if(userRepository.findByEmailAndPassword(email, pwd) != null ) {
                returnData.setStatus(true);
                returnData.setMessage("Login successfull");
                Users user = userRepository.findByEmailAndPassword(email, pwd).get();
                Optional<Users> reporter = userRepository.findByUserId(user.getReporterId());
                Userresponse userData = new Userresponse(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhone(), reporter.get().getUserName());
                Optional<Role> role = roleRepository.findById(user.getRoleId());
                userData.setUserRole(role.get().getRoleName());
                returnData.setUserData(userData);
            }
            else {
                returnData.setStatus(false);
                returnData.setMessage("Kindly enter correct credentials!");
                returnData.setUserData(null);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            returnData.setStatus(false);
            returnData.setMessage("Some error occurred. Kindly enter correct credentials!");
            returnData.setUserData(null);
        }
        return returnData;
    }

    @Override
    public ReturnData login(UserRequest userRequest){
        ReturnData returnData = new ReturnData();
        try{
            if(userRepository.findByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword()) != null ) {

                returnData.setStatus(true);
                returnData.setMessage("Login successfull");
                Users user = userRepository.findByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword()).get();
                Optional<Users> reporter = userRepository.findByUserId(user.getReporterId());
                Userresponse userData = new Userresponse(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhone(), reporter.get().getUserName());
                Optional<Role> role = roleRepository.findById(user.getRoleId());
                userData.setUserRole(role.get().getRoleName());
                returnData.setUserData(userData);
            }
            else {
                returnData.setStatus(false);
                returnData.setMessage("Kindly enter correct credentials!");
                returnData.setUserData(null);
            }
        }catch(Exception e){
            returnData.setStatus(false);
            returnData.setMessage("Kindly enter correct credentials!");
            returnData.setUserData(null);
        }
        return returnData;
    }

    @Override
    public  List<Users> displayAllUsers() {
        Pageable paging = PageRequest.of(0, 5);
        return userRepository.findAll(paging).toList();
    }

    @Override
    public ReturnData displayByUserId(String userId) {
        ReturnData returnData = new ReturnData();
        try {
            if(userRepository.findById(userId).get()!=null) {
                returnData.setStatus(true);
                returnData.setMessage("Data fetched successfully");
                Users user = userRepository.findById(userId).get();
                Optional<Users> reporter = userRepository.findByUserId(user.getReporterId());
                Userresponse userData = new Userresponse(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhone(), reporter.get().getUserName());
                Optional<Role> role = roleRepository.findById(user.getRoleId());
                userData.setUserRole(role.get().getRoleName());
                returnData.setUserData(userData);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            returnData.setStatus(false);
            returnData.setMessage("User does not exist!");
            returnData.setUserData(null);
        }
        return returnData;
    }

}
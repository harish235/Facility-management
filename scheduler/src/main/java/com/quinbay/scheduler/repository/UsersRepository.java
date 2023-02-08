package com.quinbay.scheduler.repository;

import com.quinbay.scheduler.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUserId(String userId);
    ArrayList<Users> findAll();
}

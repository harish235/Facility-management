package com.quinbay.users.repository;

import com.quinbay.users.model.Users;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByEmailAndPassword(String email, String password);
    Optional<Users> findByReporterId(int reporterId);
    Optional<Users> findByUserId(int userId);

}

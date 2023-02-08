package com.quinbay.issues.repository;

import com.quinbay.issues.model.Issues;
import com.quinbay.issues.model.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface   IssueRepository extends JpaRepository<Issues, Integer> {
    List<Issues> findAll();
    List<Issues> findByAssigneeUserId(int id);
//    List<Issues> findAllByAssigneeUserId(int assigneeUserId);
    Optional<Issues> findById(int issueId);
//    String deleteByIssueId(String issueId);
    List<Issues> findByStatus(StatusType status);
    List<Issues> findByIssueCategory(String category);
    List<Issues> findByPriority(String priority);
    Optional<Issues> findByAssigneeUserIdAndId(int userId, int issueId);
    Page<Issues> findByAssigneeUserId(int userId, Pageable pageable);
}

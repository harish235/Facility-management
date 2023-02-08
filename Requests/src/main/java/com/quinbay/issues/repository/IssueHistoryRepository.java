package com.quinbay.issues.repository;

import com.quinbay.issues.model.IssueHistory;
import com.quinbay.issues.model.Issues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueHistoryRepository extends JpaRepository<IssueHistory, Integer> {
    List<IssueHistory> findByIssueIdOrderByUpdatedDate(int issueId);
}

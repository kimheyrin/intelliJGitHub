package com.project3.messageboard.issue.domain.repository;

import com.project3.messageboard.issue.domain.entity.IssueBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueBoardRepository extends JpaRepository<IssueBoard, Long> {
}

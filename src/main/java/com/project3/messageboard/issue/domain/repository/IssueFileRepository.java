package com.project3.messageboard.issue.domain.repository;

import com.project3.messageboard.issue.domain.entity.IssueFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueFileRepository extends JpaRepository<IssueFile, Long> {
}

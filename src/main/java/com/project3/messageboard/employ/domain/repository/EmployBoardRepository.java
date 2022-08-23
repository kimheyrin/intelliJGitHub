package com.project3.messageboard.employ.domain.repository;

import com.project3.messageboard.employ.domain.entity.EmployBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployBoardRepository extends JpaRepository<EmployBoard, Long> {
}

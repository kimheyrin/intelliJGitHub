package com.project3.messageboard.study.domain.repository;

import com.project3.messageboard.study.domain.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
}

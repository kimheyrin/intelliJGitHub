package com.project3.messageboard.study.domain.repository;

import com.project3.messageboard.study.domain.entity.StudyFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyFileRepository extends JpaRepository<StudyFile, Long> {
}

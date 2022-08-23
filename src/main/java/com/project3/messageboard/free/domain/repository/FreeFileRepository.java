package com.project3.messageboard.free.domain.repository;

import com.project3.messageboard.free.domain.entity.FreeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeFileRepository extends JpaRepository<FreeFile, Long> {
}

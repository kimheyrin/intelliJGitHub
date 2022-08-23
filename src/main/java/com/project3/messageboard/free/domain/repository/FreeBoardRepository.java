package com.project3.messageboard.free.domain.repository;

import com.project3.messageboard.free.domain.entity.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
}

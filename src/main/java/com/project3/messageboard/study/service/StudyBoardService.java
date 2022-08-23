package com.project3.messageboard.study.service;

import com.project3.messageboard.study.domain.entity.StudyBoard;
import com.project3.messageboard.study.domain.repository.StudyBoardRepository;
import com.project3.messageboard.study.dto.StudyBoardDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service //핵심 비즈니스 로직을 담은 서비스 클래스를 빈으로 등록
public class StudyBoardService {
    private StudyBoardRepository StudyboardRepository;

    public StudyBoardService(StudyBoardRepository StudyboardRepository) {
        this.StudyboardRepository = StudyboardRepository;
    }

    @Transactional //해당 범위 내 메서드가 트랜잭션이 되도록 보장, 선언적 트랜잭션
    //데이터베이스 트랜잭션은 데이터베이스 관리 시스템 또는 유사한 시스템에서 상호작용의 단위, 더 이상 쪼개질 수 없는 최소의 연산
    public Long savePost(StudyBoardDto StudyboardDto) {
        return StudyboardRepository.save(StudyboardDto.toEntity()).getId();
    }

    @Transactional
    public List<StudyBoardDto> getBoardList() {
        List<StudyBoard> boardList = StudyboardRepository.findAll();
        List<StudyBoardDto> StudyboardDtoList = new ArrayList<>();

        for(StudyBoard board : boardList) {
            StudyBoardDto StudyboardDto = StudyBoardDto.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            StudyboardDtoList.add(StudyboardDto);
        }
        return StudyboardDtoList;
    }
    @Transactional
    public StudyBoardDto getPost(Long id) {
        StudyBoard board = StudyboardRepository.findById(id).get();

        StudyBoardDto StudyboardDto = StudyBoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .fileId(board.getFileId())
                .createdDate(board.getCreatedDate())
                .build();
        return StudyboardDto;
    }
    @Transactional
    public void deletePost(Long id) {
        StudyboardRepository.deleteById(id);
    }
}
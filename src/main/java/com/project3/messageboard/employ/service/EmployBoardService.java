package com.project3.messageboard.employ.service;

import com.project3.messageboard.employ.domain.entity.EmployBoard;
import com.project3.messageboard.employ.domain.repository.EmployBoardRepository;
import com.project3.messageboard.employ.dto.EmployBoardDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service //핵심 비즈니스 로직을 담은 서비스 클래스를 빈으로 등록
public class EmployBoardService {
    private EmployBoardRepository EmployboardRepository;

    public EmployBoardService(EmployBoardRepository EmployboardRepository) {
        this.EmployboardRepository = EmployboardRepository;
    }

    @Transactional //해당 범위 내 메서드가 트랜잭션이 되도록 보장, 선언적 트랜잭션
    //데이터베이스 트랜잭션은 데이터베이스 관리 시스템 또는 유사한 시스템에서 상호작용의 단위, 더 이상 쪼개질 수 없는 최소의 연산
    public Long savePost(EmployBoardDto EmployboardDto) {
        return EmployboardRepository.save(EmployboardDto.toEntity()).getId();
    }

    @Transactional
    public List<EmployBoardDto> getBoardList() {
        List<EmployBoard> boardList = EmployboardRepository.findAll();
        List<EmployBoardDto> EmployboardDtoList = new ArrayList<>();

        for(EmployBoard board : boardList) {
            EmployBoardDto EmployboardDto = EmployBoardDto.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            EmployboardDtoList.add(EmployboardDto);
        }
        return EmployboardDtoList;
    }
    @Transactional
    public EmployBoardDto getPost(Long id) {
        EmployBoard board = EmployboardRepository.findById(id).get();

        EmployBoardDto EmployboardDto = EmployBoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .fileId(board.getFileId())
                .createdDate(board.getCreatedDate())
                .build();
        return EmployboardDto;
    }
    @Transactional
    public void deletePost(Long id) {
        EmployboardRepository.deleteById(id);
    }
}
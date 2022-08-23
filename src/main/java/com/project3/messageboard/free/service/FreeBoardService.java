package com.project3.messageboard.free.service;

import com.project3.messageboard.free.domain.entity.FreeBoard;
import com.project3.messageboard.free.domain.repository.FreeBoardRepository;
import com.project3.messageboard.free.dto.FreeBoardDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service //핵심 비즈니스 로직을 담은 서비스 클래스를 빈으로 등록
public class FreeBoardService {
    private FreeBoardRepository FreeboardRepository;

    public FreeBoardService(FreeBoardRepository FreeboardRepository) {
        this.FreeboardRepository = FreeboardRepository;
    }

    @Transactional //해당 범위 내 메서드가 트랜잭션이 되도록 보장, 선언적 트랜잭션
    //데이터베이스 트랜잭션은 데이터베이스 관리 시스템 또는 유사한 시스템에서 상호작용의 단위, 더 이상 쪼개질 수 없는 최소의 연산
    public Long savePost(FreeBoardDto FreeboardDto) {
        return FreeboardRepository.save(FreeboardDto.toEntity()).getId();
    }

    @Transactional
    public List<FreeBoardDto> getBoardList() {
        List<FreeBoard> boardList = FreeboardRepository.findAll();
        List<FreeBoardDto> FreeboardDtoList = new ArrayList<>();

        for(FreeBoard board : boardList) {
            FreeBoardDto FreeboardDto = FreeBoardDto.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            FreeboardDtoList.add(FreeboardDto);
        }
        return FreeboardDtoList;
    }
    @Transactional
    public FreeBoardDto getPost(Long id) {
        FreeBoard board = FreeboardRepository.findById(id).get();

        FreeBoardDto FreeboardDto = FreeBoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .fileId(board.getFileId())
                .createdDate(board.getCreatedDate())
                .build();
        return FreeboardDto;
    }
    @Transactional
    public void deletePost(Long id) {
        FreeboardRepository.deleteById(id);
    }
}
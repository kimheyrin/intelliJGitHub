package com.project3.messageboard.issue.service;

import com.project3.messageboard.issue.domain.entity.IssueBoard;
import com.project3.messageboard.issue.domain.repository.IssueBoardRepository;
import com.project3.messageboard.issue.dto.IssueBoardDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service //핵심 비즈니스 로직을 담은 서비스 클래스를 빈으로 등록
public class IssueBoardService {
    private IssueBoardRepository IssueboardRepository;

    public IssueBoardService(IssueBoardRepository IssueboardRepository) {
        this.IssueboardRepository = IssueboardRepository;
    }

    @Transactional //해당 범위 내 메서드가 트랜잭션이 되도록 보장, 선언적 트랜잭션
    //데이터베이스 트랜잭션은 데이터베이스 관리 시스템 또는 유사한 시스템에서 상호작용의 단위, 더 이상 쪼개질 수 없는 최소의 연산
    public Long savePost(IssueBoardDto IssueboardDto) {
        return IssueboardRepository.save(IssueboardDto.toEntity()).getId();
    }

    @Transactional
    public List<IssueBoardDto> getBoardList() {
        List<IssueBoard> boardList = IssueboardRepository.findAll();
        List<IssueBoardDto> IssueboardDtoList = new ArrayList<>();

        for(IssueBoard board : boardList) {
            IssueBoardDto IssueboardDto = IssueBoardDto.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            IssueboardDtoList.add(IssueboardDto);
        }
        return IssueboardDtoList;
    }
    @Transactional
    public IssueBoardDto getPost(Long id) {
        IssueBoard board = IssueboardRepository.findById(id).get();

        IssueBoardDto IssueboardDto = IssueBoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .fileId(board.getFileId())
                .createdDate(board.getCreatedDate())
                .build();
        return IssueboardDto;
    }
    @Transactional
    public void deletePost(Long id) {
        IssueboardRepository.deleteById(id);
    }
}
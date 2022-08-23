package com.project3.messageboard.study.dto;

import com.project3.messageboard.study.domain.entity.StudyBoard;
import lombok.*;

import java.time.LocalDateTime;

//DTO(Data Transfer Object) 란 DB에서 데이터를 얻어 Service나 Controller 등으터 보낼 때 사용
@Getter //lombok 기능, 접근자 생성
@Setter //lombok 기능, 설정자 생성
@ToString // @toString 어노테이션을 이용하면 일일이 toString()메소드를 만들어줄 필요는 없음
// toString() 메소드는 객체가 가지고 있는 정보나 값들을 문자열로 만들어 리턴하는 메소드
@NoArgsConstructor //파라미터가 없는 생성자 생성
public class StudyBoardDto {
    private Long id;
    private String author;
    private String title;
    private String content;
    private Long fileId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String origFilename;

    public StudyBoard toEntity() {
        StudyBoard build = StudyBoard.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .fileId(fileId)
                .build();
        return build;
    }

    @Builder
    public StudyBoardDto(Long id, String author, String title, String content, Long fileId, LocalDateTime createdDate, LocalDateTime modifiedDate, String origFilename) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.origFilename = origFilename;
    }
}


package com.project3.messageboard.free.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter //lombok 기능, 필드 리턴
@Entity
@NoArgsConstructor //파라미터가 없는 생성자 생성
        (access = AccessLevel.PROTECTED) //접근 제한자 설정, 무분별 객체 생성 방지
@EntityListeners(AuditingEntityListener.class) //해당 클래스에 Auditing 기능을 포함
// Audit은 Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능
// Audit을 이용하면 자동으로 시간을 매핑하여 데이터베이스의 테이블에 넣어줌

public class FreeBoard {
    @Id //기본키 설정 ID 어노테이션만 사용하면 직저 할당
    @GeneratedValue(strategy= GenerationType.IDENTITY)//id 어노테이션과 같이 사용하여 기본키 자동할당
    //IDENTITY 전략을 사용해 기본키 생성을 데이터베이스에 위임
    private Long id;

    @Column(length = 10, nullable = false) //Column 어노테이션 : 객체 변수 이름과 실제 컬럼명과 다를 경우 실제 컬럼명과 매핑
    //nullable = false 은 DDL 생성 시 NOT NULL
    private String author;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private Long fileId;

    @CreatedDate //Entity가 생성되어 저장될 때 시간이 자동으로 저장
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;

    @Builder //자동으로 해당 클래스에 빌더를 추가
    public FreeBoard(Long id, String author, String title, String content, Long fileId) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }
}


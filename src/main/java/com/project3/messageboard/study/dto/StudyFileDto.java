package com.project3.messageboard.study.dto;

import com.project3.messageboard.study.domain.entity.StudyFile;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudyFileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;

    public StudyFile toEntity() {
        StudyFile build = StudyFile.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public StudyFileDto(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
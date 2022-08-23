package com.project3.messageboard.employ.dto;

import com.project3.messageboard.employ.domain.entity.EmployFile;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmployFileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;

    public EmployFile toEntity() {
        EmployFile build = EmployFile.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public EmployFileDto(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
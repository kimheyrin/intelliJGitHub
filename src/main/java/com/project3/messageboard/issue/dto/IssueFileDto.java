package com.project3.messageboard.issue.dto;

import com.project3.messageboard.issue.domain.entity.IssueFile;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IssueFileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;

    public IssueFile toEntity() {
        IssueFile build = IssueFile.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public IssueFileDto(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
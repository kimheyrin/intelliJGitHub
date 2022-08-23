package com.project3.messageboard.free.dto;

import com.project3.messageboard.free.domain.entity.FreeFile;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FreeFileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;

    public FreeFile toEntity() {
        FreeFile build = FreeFile.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public FreeFileDto(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
package com.project3.messageboard.free.service;

import com.project3.messageboard.free.domain.entity.FreeFile;
import com.project3.messageboard.free.domain.repository.FreeFileRepository;
import com.project3.messageboard.free.dto.FreeFileDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FreeFileService {
    private FreeFileRepository fileRepository;

    public FreeFileService(FreeFileRepository fileRepository) {

        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(FreeFileDto fileDto) {

        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public FreeFileDto getFile(Long id) {
        FreeFile file = fileRepository.findById(id).get();

        FreeFileDto fileDto = FreeFileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}
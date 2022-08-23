package com.project3.messageboard.employ.service;

import com.project3.messageboard.employ.domain.entity.EmployFile;
import com.project3.messageboard.employ.domain.repository.EmployFileRepository;
import com.project3.messageboard.employ.dto.EmployFileDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmployFileService {
    private EmployFileRepository fileRepository;

    public EmployFileService(EmployFileRepository fileRepository) {

        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(EmployFileDto fileDto) {

        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public EmployFileDto getFile(Long id) {
        EmployFile file = fileRepository.findById(id).get();

        EmployFileDto fileDto = EmployFileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}
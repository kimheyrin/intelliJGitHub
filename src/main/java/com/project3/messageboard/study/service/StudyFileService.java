package com.project3.messageboard.study.service;

import com.project3.messageboard.study.domain.entity.StudyFile;
import com.project3.messageboard.study.domain.repository.StudyFileRepository;
import com.project3.messageboard.study.dto.StudyFileDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StudyFileService {
    private StudyFileRepository fileRepository;

    public StudyFileService(StudyFileRepository fileRepository) {

        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(StudyFileDto fileDto) {

        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public StudyFileDto getFile(Long id) {
        StudyFile file = fileRepository.findById(id).get();

        StudyFileDto fileDto = StudyFileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}
package com.project3.messageboard.issue.service;

import com.project3.messageboard.issue.domain.entity.IssueFile;
import com.project3.messageboard.issue.domain.repository.IssueFileRepository;
import com.project3.messageboard.issue.dto.IssueFileDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IssueFileService {
    private IssueFileRepository fileRepository;

    public IssueFileService(IssueFileRepository fileRepository) {

        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(IssueFileDto fileDto) {

        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public IssueFileDto getFile(Long id) {
        IssueFile file = fileRepository.findById(id).get();

        IssueFileDto fileDto = IssueFileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}
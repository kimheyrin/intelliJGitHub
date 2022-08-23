package com.project3.messageboard.Controller;

import com.project3.messageboard.employ.dto.EmployBoardDto;
import com.project3.messageboard.employ.dto.EmployFileDto;
import com.project3.messageboard.employ.service.EmployBoardService;
import com.project3.messageboard.employ.service.EmployFileService;
import com.project3.messageboard.employ.util.EmployMD5Generator;
import com.project3.messageboard.free.dto.FreeBoardDto;
import com.project3.messageboard.free.dto.FreeFileDto;
import com.project3.messageboard.free.service.FreeBoardService;
import com.project3.messageboard.free.service.FreeFileService;
import com.project3.messageboard.free.util.FreeMD5Generator;
import com.project3.messageboard.issue.dto.IssueBoardDto;
import com.project3.messageboard.issue.dto.IssueFileDto;
import com.project3.messageboard.issue.service.IssueBoardService;
import com.project3.messageboard.issue.service.IssueFileService;
import com.project3.messageboard.issue.util.IssueMD5Generator;
import com.project3.messageboard.study.dto.StudyBoardDto;
import com.project3.messageboard.study.dto.StudyFileDto;
import com.project3.messageboard.study.service.StudyBoardService;
import com.project3.messageboard.study.service.StudyFileService;
import com.project3.messageboard.study.util.StudyMD5Generator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller // 사용자의 요청에 따라 어떤 처리를 할지 결정 역할 분담이 핵심
public class BoardController {

    public BoardController(FreeBoardService FreeboardService, FreeFileService FreefileService,
                           StudyBoardService StudyboardService, StudyFileService StudyfileService,
                           EmployBoardService EmployboardService, EmployFileService EmployfileService,
                           IssueBoardService IssueboardService, IssueFileService IssuefileService) {
        this.FreeboardService = FreeboardService;
        this.FreefileService = FreefileService;

        this.StudyboardService = StudyboardService;
        this.StudyfileService = StudyfileService;

        this.EmployboardService = EmployboardService;
        this.EmployfileService = EmployfileService;

        this.IssueboardService = IssueboardService;
        this.IssuefileService = IssuefileService;
    }

    /***********************    홈   ***************************/
    @GetMapping("/main/home")
    public String home() {

        return "main/home";
    }

    /***********************    자유 게시판   ***************************/
    private FreeBoardService FreeboardService;
    private FreeFileService FreefileService;

    @GetMapping("/freeboard/list")  // HTTP GET 요청을 처리하는 매서드를 맵핑하는 어노테이션. 메서드(url)레 까라 어떤 페이지를 보여줄지 결정하는 역할
    public String freelist(Model model) {
        List<FreeBoardDto> FreeboardDtoList = FreeboardService.getBoardList();
        model.addAttribute("postList", FreeboardDtoList);
        return "freeboard/list";
    }

    @GetMapping("/freeboard/post")
    public String freepost() {

        return "freeboard/post.html";
    }

    @PostMapping("/freeboard/post") //주어진 url 표현식과 일치하는 HTTP POST 요청 처리. 서버로 데이터를 전송해 서버에 변경사항을 만듬
    public String freewrite(@RequestParam("file") MultipartFile files, FreeBoardDto FreeboardDto) {
        try {
            String origFilename = files.getOriginalFilename();
            String filename = new FreeMD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FreeFileDto FreefileDto = new FreeFileDto();
            FreefileDto.setOrigFilename(origFilename);
            FreefileDto.setFilename(filename);
            FreefileDto.setFilePath(filePath);

            Long fileId = FreefileService.saveFile(FreefileDto);
            FreeboardDto.setFileId(fileId);
            FreeboardService.savePost(FreeboardDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/freeboard/list";
    }
    @GetMapping("/freeboard/post/{id}")
    public String freedetail(@PathVariable("id") Long id, Model model) {
        FreeBoardDto FreeboardDto = FreeboardService.getPost(id);
        model.addAttribute("post", FreeboardDto);
        return "freeboard/detail.html";
    }
    @GetMapping("/freeboard/post/edit/{id}")
    public String freeedit(@PathVariable("id") Long id, Model model) {
        FreeBoardDto FreeboardDto = FreeboardService.getPost(id);
        model.addAttribute("post", FreeboardDto);
        return "freeboard/edit.html";
    }
    @PutMapping("/freeboard/post/edit/{id}")
    public String freeupdate(FreeBoardDto FreeboardDto) {
        FreeboardService.savePost(FreeboardDto);
        return "redirect:/freeboard/list";
    }
    @DeleteMapping("/freeboard/post/{id}")
    public String freedelete(@PathVariable("id") Long id) {
        FreeboardService.deletePost(id);
        return "redirect:/freeboard/list";
    }
    @GetMapping("/freeboard/download/{fileId}")
    public ResponseEntity<Resource> freefileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        FreeFileDto FreefileDto = FreefileService.getFile(fileId);
        Path path = Paths.get(FreefileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FreefileDto.getOrigFilename() + "\"")
                .body(resource);
    }

    /***********************   학업 게시판   ***************************/
    private StudyBoardService StudyboardService;
    private StudyFileService StudyfileService;

    @GetMapping("/studyboard/list")  // HTTP GET 요청을 처리하는 매서드를 맵핑하는 어노테이션. 메서드(url)레 까라 어떤 페이지를 보여줄지 결정하는 역할
    public String studylist(Model model) {
        List<StudyBoardDto> StudyboardDtoList = StudyboardService.getBoardList();
        model.addAttribute("postList", StudyboardDtoList);
        return "studyboard/list";
    }

    @GetMapping("/studyboard/post")
    public String studypost() {

        return "studyboard/post.html";
    }

    @PostMapping("/studyboard/post") //주어진 url 표현식과 일치하는 HTTP POST 요청 처리. 서버로 데이터를 전송해 서버에 변경사항을 만듬
    public String studywrite(@RequestParam("file") MultipartFile files, StudyBoardDto StudyboardDto) {
        try {
            String origFilename = files.getOriginalFilename();
            String filename = new StudyMD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            StudyFileDto StudyfileDto = new StudyFileDto();
            StudyfileDto.setOrigFilename(origFilename);
            StudyfileDto.setFilename(filename);
            StudyfileDto.setFilePath(filePath);

            Long fileId = StudyfileService.saveFile(StudyfileDto);
            StudyboardDto.setFileId(fileId);
            StudyboardService.savePost(StudyboardDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/studyboard/list";
    }
    @GetMapping("/studyboard/post/{id}")
    public String studydetail(@PathVariable("id") Long id, Model model) {
        StudyBoardDto StudyboardDto = StudyboardService.getPost(id);
        model.addAttribute("post", StudyboardDto);
        return "studyboard/detail.html";
    }
    @GetMapping("/studyboard/post/edit/{id}")
    public String studyedit(@PathVariable("id") Long id, Model model) {
        StudyBoardDto StudyboardDto = StudyboardService.getPost(id);
        model.addAttribute("post", StudyboardDto);
        return "studyboard/edit.html";
    }
    @PutMapping("/studyboard/post/edit/{id}")
    public String studyupdate(StudyBoardDto StudyboardDto) {
        StudyboardService.savePost(StudyboardDto);
        return "redirect:/studyboard/list";
    }
    @DeleteMapping("/studyboard/post/{id}")
    public String studydelete(@PathVariable("id") Long id) {
        StudyboardService.deletePost(id);
        return "redirect:/studyboard/list";
    }
    @GetMapping("/studyboard/download/{fileId}")
    public ResponseEntity<Resource> studyfileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        StudyFileDto StudyfileDto = StudyfileService.getFile(fileId);
        Path path = Paths.get(StudyfileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + StudyfileDto.getOrigFilename() + "\"")
                .body(resource);
    }

    /***********************   취업 게시판   ***************************/
    private EmployBoardService EmployboardService;
    private EmployFileService EmployfileService;

    @GetMapping("/employboard/list")  // HTTP GET 요청을 처리하는 매서드를 맵핑하는 어노테이션. 메서드(url)레 까라 어떤 페이지를 보여줄지 결정하는 역할
    public String employlist(Model model) {
        List<EmployBoardDto> EmployboardDtoList = EmployboardService.getBoardList();
        model.addAttribute("postList", EmployboardDtoList);
        return "employboard/list";
    }

    @GetMapping("/employboard/post")
    public String employpost() {

        return "employboard/post.html";
    }

    @PostMapping("/employboard/post") //주어진 url 표현식과 일치하는 HTTP POST 요청 처리. 서버로 데이터를 전송해 서버에 변경사항을 만듬
    public String employwrite(@RequestParam("file") MultipartFile files, EmployBoardDto EmployboardDto) {
        try {
            String origFilename = files.getOriginalFilename();
            String filename = new EmployMD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            EmployFileDto EmployfileDto = new EmployFileDto();
            EmployfileDto.setOrigFilename(origFilename);
            EmployfileDto.setFilename(filename);
            EmployfileDto.setFilePath(filePath);

            Long fileId = EmployfileService.saveFile(EmployfileDto);
            EmployboardDto.setFileId(fileId);
            EmployboardService.savePost(EmployboardDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/employboard/list";
    }
    @GetMapping("/employboard/post/{id}")
    public String employdetail(@PathVariable("id") Long id, Model model) {
        EmployBoardDto EmployboardDto = EmployboardService.getPost(id);
        model.addAttribute("post", EmployboardDto);
        return "employboard/detail.html";
    }
    @GetMapping("/employboard/post/edit/{id}")
    public String employedit(@PathVariable("id") Long id, Model model) {
        EmployBoardDto EmployboardDto = EmployboardService.getPost(id);
        model.addAttribute("post", EmployboardDto);
        return "employboard/edit.html";
    }
    @PutMapping("/employboard/post/edit/{id}")
    public String employupdate(EmployBoardDto EmployboardDto) {
        EmployboardService.savePost(EmployboardDto);
        return "redirect:/employboard/list";
    }
    @DeleteMapping("/employboard/post/{id}")
    public String employdelete(@PathVariable("id") Long id) {
        EmployboardService.deletePost(id);
        return "redirect:/employboard/list";
    }
    @GetMapping("/employboard/download/{fileId}")
    public ResponseEntity<Resource> employfileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        EmployFileDto EmployfileDto = EmployfileService.getFile(fileId);
        Path path = Paths.get(EmployfileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + EmployfileDto.getOrigFilename() + "\"")
                .body(resource);
    }

    /***********************    이슈 게시판   ***************************/
    private IssueBoardService IssueboardService;
    private IssueFileService IssuefileService;

    @GetMapping("/issueboard/list")  // HTTP GET 요청을 처리하는 매서드를 맵핑하는 어노테이션. 메서드(url)레 까라 어떤 페이지를 보여줄지 결정하는 역할
    public String issuelist(Model model) {
        List<IssueBoardDto> IssueboardDtoList = IssueboardService.getBoardList();
        model.addAttribute("postList", IssueboardDtoList);
        return "issueboard/list";
    }

    @GetMapping("/issueboard/post")
    public String issuepost() {

        return "issueboard/post.html";
    }

    @PostMapping("/issueboard/post") //주어진 url 표현식과 일치하는 HTTP POST 요청 처리. 서버로 데이터를 전송해 서버에 변경사항을 만듬
    public String issuewrite(@RequestParam("file") MultipartFile files, IssueBoardDto IssueboardDto) {
        try {
            String origFilename = files.getOriginalFilename();
            String filename = new IssueMD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            IssueFileDto IssuefileDto = new IssueFileDto();
            IssuefileDto.setOrigFilename(origFilename);
            IssuefileDto.setFilename(filename);
            IssuefileDto.setFilePath(filePath);

            Long fileId = IssuefileService.saveFile(IssuefileDto);
            IssueboardDto.setFileId(fileId);
            IssueboardService.savePost(IssueboardDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/issueboard/list";
    }
    @GetMapping("/issueboard/post/{id}")
    public String issuedetail(@PathVariable("id") Long id, Model model) {
        IssueBoardDto IssueboardDto = IssueboardService.getPost(id);
        model.addAttribute("post", IssueboardDto);
        return "issueboard/detail.html";
    }
    @GetMapping("/issueboard/post/edit/{id}")
    public String issueedit(@PathVariable("id") Long id, Model model) {
        IssueBoardDto IssueboardDto = IssueboardService.getPost(id);
        model.addAttribute("post", IssueboardDto);
        return "issueboard/edit.html";
    }
    @PutMapping("/issueboard/post/edit/{id}")
    public String issueupdate(IssueBoardDto IssueboardDto) {
        IssueboardService.savePost(IssueboardDto);
        return "redirect:/issueboard/list";
    }
    @DeleteMapping("/issueboard/post/{id}")
    public String issuedelete(@PathVariable("id") Long id) {
        IssueboardService.deletePost(id);
        return "redirect:/issueboard/list";
    }
    @GetMapping("/issueboard/download/{fileId}")
    public ResponseEntity<Resource> issuefileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        IssueFileDto IssuefileDto = IssuefileService.getFile(fileId);
        Path path = Paths.get(IssuefileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + IssuefileDto.getOrigFilename() + "\"")
                .body(resource);
    }

}
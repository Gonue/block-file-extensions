package com.example.fileblocking.domain.controller;

import com.example.fileblocking.domain.dto.UploadFileDto;
import com.example.fileblocking.domain.service.UploadFileService;
import com.example.fileblocking.global.error.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/upload-file")
public class UploadFileController {

    private final UploadFileService uploadFileService;

    //파일 업로드
    @PostMapping
    public Response<UploadFileDto> uploadFile(@RequestParam("file") MultipartFile file) {
        return Response.success(uploadFileService.uploadFile(file));
    }

    //파일 목록
    @GetMapping
    public Response<List<UploadFileDto>> getFiles() {
        return Response.success(uploadFileService.getFiles());
    }

    //파일 다운로드
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Resource resource = uploadFileService.downloadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //파일 삭제
    @DeleteMapping("/{id}")
    public Response<Void> deleteFile(@PathVariable Long id) {
        uploadFileService.deleteFile(id);
        return Response.success();
    }
}

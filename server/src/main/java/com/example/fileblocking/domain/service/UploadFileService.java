package com.example.fileblocking.domain.service;

import com.example.fileblocking.domain.dto.UploadFileDto;
import com.example.fileblocking.domain.entity.FixedExtensionEntity;
import com.example.fileblocking.domain.entity.UploadFileEntity;
import com.example.fileblocking.domain.repository.CustomExtensionRepository;
import com.example.fileblocking.domain.repository.FixedExtensionRepository;
import com.example.fileblocking.domain.repository.UploadFileRepository;
import com.example.fileblocking.global.error.exception.BusinessLogicException;
import com.example.fileblocking.global.error.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;
    private final FixedExtensionRepository fixedExtensionRepository;
    private final CustomExtensionRepository customExtensionRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    //파일 업로드
    @Transactional
    public UploadFileDto uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.FILE_IS_EMPTY, "파일이 존재하지 않습니다.");
        }

        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));

        if (fixedExtensionRepository.findByExtension(extension).map(FixedExtensionEntity::isActive).orElse(false)
            || customExtensionRepository.findByExtension(extension).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_EXTENSION_FILE, "업로드 불가능한 파일 확장자입니다.");
        }

        try {
            byte[] bytes = file.getBytes();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String newFileName = timestamp + "-" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + newFileName);
            Files.write(path, bytes);

            UploadFileEntity entity = new UploadFileEntity();
            entity.setFileName(newFileName);
            entity.setFilePath(uploadDir + newFileName);
            entity.setExtension(extension);
            uploadFileRepository.save(entity);

            return UploadFileDto.from(entity);
        } catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_ERROR, "파일 업로드 중 에러가 발생했습니다.");
        }
    }

    //파일 목록
    @Transactional(readOnly = true)
    public List<UploadFileDto> getFiles() {
        return uploadFileRepository.findAll()
                .stream()
                .map(UploadFileDto::from)
                .collect(Collectors.toList());
    }

    //파일 다운로드
    public Resource downloadFile(Long id) {
        UploadFileEntity entity = uploadFileRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FILE_NOT_FOUND, "해당 파일을 찾을 수 없습니다."));
        try {
            Path filePath = Paths.get(entity.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BusinessLogicException(ExceptionCode.FILE_DOWNLOAD_ERROR, "파일을 다운로드할 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new BusinessLogicException(ExceptionCode.FILE_DOWNLOAD_ERROR, "파일을 다운로드할 수 없습니다.");
        }
    }

    //파일 삭제
    @Transactional
    public void deleteFile(Long id) {
        UploadFileEntity entity = uploadFileRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FILE_NOT_FOUND, "해당 파일을 찾을 수 없습니다."));
        try {
            Path filePath = Paths.get(entity.getFilePath()).normalize();
            Files.deleteIfExists(filePath);
            uploadFileRepository.deleteById(id);
        } catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.FILE_DELETE_ERROR, "파일을 삭제할 수 없습니다.");
        }
    }



    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}

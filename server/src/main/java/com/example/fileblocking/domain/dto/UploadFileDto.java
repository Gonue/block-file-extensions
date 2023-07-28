package com.example.fileblocking.domain.dto;

import com.example.fileblocking.domain.entity.UploadFileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDto {

    private Long id;
    private String fileName;
    private String filePath;
    private String extension;

    public static UploadFileDto from(UploadFileEntity entity){
        return new UploadFileDto(
                entity.getId(),
                entity.getFileName(),
                entity.getFilePath(),
                entity.getExtension()
        );
    }
}
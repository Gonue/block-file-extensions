package com.example.fileblocking.domain.dto;

import com.example.fileblocking.domain.entity.CustomExtensionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomExtensionDto {

    private Long id;
    private String extension;

    public static CustomExtensionDto from(CustomExtensionEntity entity){
        return new CustomExtensionDto(
                entity.getId(),
                entity.getExtension()
        );
    }
}

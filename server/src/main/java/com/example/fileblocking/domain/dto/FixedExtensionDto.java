package com.example.fileblocking.domain.dto;

import com.example.fileblocking.domain.entity.FixedExtensionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FixedExtensionDto {

    private Long id;
    private String extension;
    private boolean isActive;

    public static FixedExtensionDto from(FixedExtensionEntity entity){
        return new FixedExtensionDto(
                entity.getId(),
                entity.getExtension(),
                entity.isActive()
        );
    }
}

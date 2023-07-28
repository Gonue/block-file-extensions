package com.example.fileblocking.domain.service;

import com.example.fileblocking.domain.dto.CustomExtensionDto;
import com.example.fileblocking.domain.entity.CustomExtensionEntity;
import com.example.fileblocking.domain.repository.CustomExtensionRepository;
import com.example.fileblocking.domain.repository.FixedExtensionRepository;
import com.example.fileblocking.global.error.exception.BusinessLogicException;
import com.example.fileblocking.global.error.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomExtensionService {

    private final CustomExtensionRepository customExtensionRepository;
    private final FixedExtensionRepository fixedExtensionRepository;

    //커스텀 확장자 추가
    @Transactional
    public CustomExtensionDto addExtension(CustomExtensionDto customExtensionDto) {

        if (customExtensionDto.getExtension().length() < 1 || customExtensionDto.getExtension().length() > 20) {
            throw new BusinessLogicException(ExceptionCode.INVALID_EXTENSION_LENGTH, "확장자의 길이는 1자 이상, 20자 이하여야 합니다.");
        }

        if(!customExtensionDto.getExtension().matches("[a-zA-Z]+")){
            throw new BusinessLogicException(ExceptionCode.INVALID_EXTENSION_FORMAT, "확장자는 영문자로만 구성되어야 합니다.");
        }

        if (customExtensionRepository.count() >= 200) {
            throw new BusinessLogicException(ExceptionCode.TOO_MANY_EXTENSIONS, "확장자의 수는 200개를 넘을 수 없습니다.");
        }

        if (customExtensionRepository.findByExtension(customExtensionDto.getExtension()).isPresent()
                || fixedExtensionRepository.findByExtension(customExtensionDto.getExtension()).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.EXTENSION_ALREADY_EXISTS_OR_REGISTERED_FIXED_EXTENSION, "이 확장자는 이미 존재하거나 고정확장자에 등록되어 있습니다.");
        }

        CustomExtensionEntity entity = new CustomExtensionEntity();
        entity.setExtension(customExtensionDto.getExtension());
        customExtensionRepository.save(entity);
        return CustomExtensionDto.from(entity);
    }

    //커스텀 확장자 삭제
    @Transactional
    public void deleteExtension(Long id) {
        if (!customExtensionRepository.existsById(id)) {
            throw new BusinessLogicException(ExceptionCode.EXTENSION_NOT_FOUND, "해당 확장자가 존재하지 않습니다.");
        }
        customExtensionRepository.deleteById(id);
    }

    //커스텀 확장자 리스트
    @Transactional(readOnly = true)
    public List<CustomExtensionDto> getExtensions() {
        return customExtensionRepository.findAll()
                .stream()
                .map(CustomExtensionDto::from)
                .collect(Collectors.toList());
    }
}

package com.example.fileblocking.domain.service;

import com.example.fileblocking.domain.dto.FixedExtensionDto;
import com.example.fileblocking.domain.entity.FixedExtensionEntity;
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
public class FixedExtensionService {

    private final FixedExtensionRepository fixedExtensionRepository;
    private final CustomExtensionRepository customExtensionRepository;

    //고정 확장자 추가
    @Transactional
    public FixedExtensionDto addExtension(FixedExtensionDto fixedExtensionDto) {

        if(fixedExtensionDto.getExtension().length() < 1 || fixedExtensionDto.getExtension().length() > 20) {
            throw new BusinessLogicException(ExceptionCode.INVALID_EXTENSION_LENGTH, "확장자의 길이는 1자 이상, 20자 이하여야 합니다.");
        }
        if(!fixedExtensionDto.getExtension().matches("[a-zA-Z]+")) {
            throw new BusinessLogicException(ExceptionCode.INVALID_EXTENSION_FORMAT, "확장자는 영문자로만 구성되어야 합니다.");
        }
        if(fixedExtensionRepository.count() >= 200){
            throw new BusinessLogicException(ExceptionCode.TOO_MANY_EXTENSIONS, "확장자의 수는 200개를 넘을 수 없습니다.");
        }

        customExtensionRepository.findByExtension(fixedExtensionDto.getExtension()).ifPresent(customExtensionRepository::delete);

        fixedExtensionRepository.findByExtension(fixedExtensionDto.getExtension()).ifPresent(it -> {
            throw new BusinessLogicException(ExceptionCode.EXTENSION_ALREADY_EXISTS, "이 확장자는 이미 존재 합니다.");
        });

        FixedExtensionEntity entity = new FixedExtensionEntity();
        entity.setExtension(fixedExtensionDto.getExtension());
        entity.setActive(fixedExtensionDto.isActive());
        fixedExtensionRepository.save(entity);
        return FixedExtensionDto.from(entity);
    }

    //고정 확장자 삭제
    @Transactional
    public void deleteExtension(Long id) {
        fixedExtensionRepository.findById(id)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXTENSION_NOT_FOUND, "확장자를 찾을 수 없음"));
        fixedExtensionRepository.deleteById(id);
    }

    //고정 확장자 상태 변경
    @Transactional
    public FixedExtensionDto changeExtensionStatus(Long id) {
        FixedExtensionEntity entity = fixedExtensionRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXTENSION_NOT_FOUND, "확장자를 찾을 수 없음"));
        entity.setActive(!entity.isActive());
        fixedExtensionRepository.save(entity);
        return FixedExtensionDto.from(entity);
    }

    //고정 확장자 리스트
    @Transactional(readOnly = true)
    public List<FixedExtensionDto> getExtensions() {
        return fixedExtensionRepository.findAll()
                .stream()
                .map(FixedExtensionDto::from)
                .collect(Collectors.toList());
    }
}
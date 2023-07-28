package com.example.fileblocking.domain.controller;

import com.example.fileblocking.domain.dto.FixedExtensionDto;
import com.example.fileblocking.domain.service.FixedExtensionService;
import com.example.fileblocking.global.error.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fixed-extensions")
public class FixedExtensionController {

    private final FixedExtensionService fixedExtensionService;

    // 고정 확장자 추가
    @PostMapping
    public Response<FixedExtensionDto> addExtension(@RequestBody FixedExtensionDto fixedExtensionDto) {
        return Response.success(fixedExtensionService.addExtension(fixedExtensionDto));
    }

    // 고정 확장자 삭제
    @DeleteMapping("/{id}")
    public Response<Void> deleteExtension(@PathVariable Long id) {
        fixedExtensionService.deleteExtension(id);
        return Response.success();
    }

    // 고정 확장자 상태 변경
    @PutMapping("/{id}")
    public Response<FixedExtensionDto> changeExtensionStatus(@PathVariable Long id) {
        return Response.success(fixedExtensionService.changeExtensionStatus(id));
    }

    // 고정 확장자 리스트
    @GetMapping
    public Response<List<FixedExtensionDto>> getExtensions() {
        return Response.success(fixedExtensionService.getExtensions());
    }
}

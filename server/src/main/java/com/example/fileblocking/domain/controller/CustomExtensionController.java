package com.example.fileblocking.domain.controller;

import com.example.fileblocking.domain.dto.CustomExtensionDto;
import com.example.fileblocking.domain.service.CustomExtensionService;
import com.example.fileblocking.global.error.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/custom-extensions")
public class CustomExtensionController {

    private final CustomExtensionService customExtensionService;

    // 커스텀 확장자 추가
    @PostMapping
    public Response<CustomExtensionDto> addExtension(@RequestBody CustomExtensionDto customExtensionDto) {
        return Response.success(customExtensionService.addExtension(customExtensionDto));
    }

    // 커스텀 확장자 삭제
    @DeleteMapping("/{id}")
    public Response<Void> deleteExtension(@PathVariable Long id) {
        customExtensionService.deleteExtension(id);
        return Response.success();
    }

    // 커스텀 확장자 리스트
    @GetMapping
    public Response<List<CustomExtensionDto>> getExtensions() {
        return Response.success(customExtensionService.getExtensions());
    }
}

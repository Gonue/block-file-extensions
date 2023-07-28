package com.example.fileblocking.global.error.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    EXTENSION_ALREADY_EXISTS(409, "확장자가 이미 존재"),
    EXTENSION_NOT_FOUND(404,"확장자를 찾을 수 없음"),
    INVALID_EXTENSION_FILE(400,"업로드 할 수 없는 확장자"),
    INVALID_EXTENSION_LENGTH(400, "확장자의 길이는 1자 이상, 20자 이하여야 합니다."),
    INVALID_EXTENSION_FORMAT(400, "확장자는 영문자로만 구성되어야 합니다."),
    TOO_MANY_EXTENSIONS(400, "확장자의 수는 200개를 넘을 수 없습니다."),
    EXTENSION_ALREADY_EXISTS_OR_REGISTERED_FIXED_EXTENSION(409,"이 확장자는 이미 존재하거나 고정확장자에 등록되어 있습니다."),
    FILE_IS_EMPTY(404, "파일이 존재하지 않습니다."),
    FILE_UPLOAD_ERROR(404,"파일 업로드 에러"),
    FILE_NOT_FOUND(404, "파일을 찾을수 없습니다."),
    FILE_DOWNLOAD_ERROR(404, "파일을 다운로드 받을 수 없습니다."),
    FILE_DELETE_ERROR(404,"파일을 삭제할수 없습니다."),
    INTERNAL_SERVER_ERROR(500,"내부 서버 오류");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

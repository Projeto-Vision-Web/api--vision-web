package com.visionweb.app_vision_web.application.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) {}
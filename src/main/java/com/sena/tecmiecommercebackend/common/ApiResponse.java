package com.sena.tecmiecommercebackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class ApiResponse {
    private final boolean success;
    private final String message;

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}

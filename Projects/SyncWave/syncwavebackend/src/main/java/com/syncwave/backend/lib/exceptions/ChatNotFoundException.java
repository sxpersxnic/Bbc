package com.syncwave.backend.lib.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ChatNotFoundException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public ChatNotFoundException(Map<String, List<String>> errors) {
        this.errors = errors;
    }

}

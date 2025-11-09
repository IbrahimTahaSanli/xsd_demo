package com.tahasanli.xsd_demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InternalErrorException extends RuntimeException {
    private final String message;
}

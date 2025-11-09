package com.tahasanli.xsd_demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class XmlValidationException extends RuntimeException {
    private final String message;
}

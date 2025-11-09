package com.tahasanli.xsd_demo.advice;

import com.tahasanli.xsd_demo.advice.response.BadRequestResponse;
import com.tahasanli.xsd_demo.advice.response.ServerErrorResponse;
import com.tahasanli.xsd_demo.advice.response.XmlValidationErrorResponse;
import com.tahasanli.xsd_demo.exception.BadRequestException;
import com.tahasanli.xsd_demo.exception.InternalErrorException;
import com.tahasanli.xsd_demo.exception.XmlValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(XmlValidationException.class)
    public ResponseEntity<XmlValidationErrorResponse> handleValidationException(XmlValidationException exception) {
        return ResponseEntity.badRequest().body(
                XmlValidationErrorResponse.builder().message(exception.getMessage()).build()
        );
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ServerErrorResponse> handleIOException(InternalErrorException exception) {
        return ResponseEntity.internalServerError().body(
                ServerErrorResponse.builder()
                        .message("Server Side error happen try again later")
                        .build()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestResponse> handleBadRequest(BadRequestException exception) {
        return ResponseEntity.badRequest().body(
                BadRequestResponse.builder().message(exception.getMessage()).build()
        );
    }
}

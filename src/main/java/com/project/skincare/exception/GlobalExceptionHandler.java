package com.project.skincare.exception;

import com.project.skincare.model.response.GeneralErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GeneralErrorResponse> handleBusinessException(BusinessException exception, WebRequest request) {
        GeneralErrorResponse errorResponse = errorResponse(
                exception,
                request,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GeneralErrorResponse> handleNotFoundException(NotFoundException exception, WebRequest request) {
        GeneralErrorResponse errorResponse = errorResponse(
                exception,
                request,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private GeneralErrorResponse errorResponse(RuntimeException exception, WebRequest webRequest, int httpStatus, String reasonPhrase) {
        return GeneralErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus)
                .error(reasonPhrase)
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .build();
    }

}

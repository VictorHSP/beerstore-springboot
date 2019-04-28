package com.hibicode.beerstore.error;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@JsonAutoDetect(fieldVisibility = ANY)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private final int statusCode;
    private final List<ApiError> errors;


    static ErrorResponse of(HttpStatus status, List<ApiError> errors) {
        return new ErrorResponse(status.value(), errors);
    }

    static ErrorResponse of(HttpStatus status, ApiError error) {
        return new ErrorResponse(status.value(), Collections.singletonList(error));
    }


    @JsonAutoDetect(fieldVisibility = ANY)
    @RequiredArgsConstructor
    static class ApiError {
        private final String code;
        private final String message;
    }

}

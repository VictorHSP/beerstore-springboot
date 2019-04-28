package com.hibicode.beerstore.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hibicode.beerstore.service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.hibicode.beerstore.error.ErrorResponse.ApiError;

import java.util.List;
import java.util.Locale;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final MessageSource apiErrorMessageSource;
    private final String NO_MESSAGE_AVAILABLE = "No message available";
    private final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, Locale locale) {
        Stream<ObjectError> errors = exception.getBindingResult()
                .getAllErrors()
                .stream();

        List<ApiError> apiErrors = errors
                .map(ObjectError::getDefaultMessage)
                .map(code -> toApiError(code, locale))
                .collect(toList());

        return ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors));
    }


    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception, Locale locale) {
        final String errorCode = "generic-1";
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception.getValue()));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, Locale locale) {
        final String errorCode = exception.getErrorCode();
        final HttpStatus status = exception.getStatus();
        final Object[] args = exception.getArgs();
        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, args));

        return ResponseEntity.badRequest().body(errorResponse);
    }


    protected ApiError toApiError(String code, Locale locale, Object... args) {
        String message;
        try {
            message = apiErrorMessageSource.getMessage(code, args, locale);
        }catch (NoSuchMessageException ex) {
            LOG.error("Could not find any message for {} code under {} locale", code, locale);
            message = NO_MESSAGE_AVAILABLE;
        }

        return new ApiError(code, message);
    }
}

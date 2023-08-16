package onboarding.wanted.backend.global.error.handler;

import lombok.extern.slf4j.Slf4j;
import onboarding.wanted.backend.global.error.ErrorCode;
import onboarding.wanted.backend.global.error.exception.BusinessException;
import onboarding.wanted.backend.global.response.ApiResponse;
import onboarding.wanted.backend.global.response.CustomResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CustomResponse> handleException(Exception e) {
        log.warn(e.getMessage(), e);
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    protected ResponseEntity<CustomResponse> handleBusinessException(BusinessException e) {
        log.warn(e.getMessage(), e);
        return ApiResponse.error(e.getErrorCode());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    protected ResponseEntity<CustomResponse> handleNotFoundException(ChangeSetPersister.NotFoundException e) {
        log.warn(e.getMessage(), e);
        return ApiResponse.error(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CustomResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        String errorMessage = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ApiResponse.error(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<CustomResponse> handleAuthenticationException (AuthenticationException e) {
        log.warn(e.getMessage(), e);

        return ApiResponse.error(ErrorCode.LOGIN_USER_NOT_FOUND);
    }
}

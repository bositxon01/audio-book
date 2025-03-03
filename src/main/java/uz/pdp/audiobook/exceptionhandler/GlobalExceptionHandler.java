package uz.pdp.audiobook.exceptionhandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.audiobook.payload.ApiResult;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles @Valid validation errors inside DTOs
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ApiResult.error("Validation failed", errors);
    }

    // Handles @Valid for query params or path variables
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult<Map<String, String>> handleConstraintViolationExceptions(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();

        exception
                .getConstraintViolations()
                .forEach(violation ->
                        errors.put(violation.getPropertyPath().toString(), violation.getMessage())
                );

        return ApiResult.error("Invalid request parameters. Please correct the following errors:", errors);
    }

    // Handles UsernameNotFoundExceptions
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResult<Map<String, String>> handleUsernameNotFoundExceptions(UsernameNotFoundException exception) {
        Map<String, String> errors = new HashMap<>();

        errors.put("username", exception.getMessage());

        return ApiResult.error("Username not found", errors);
    }

    // Handles all other runtime exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ApiResult<String> handleRuntimeExceptions(RuntimeException exception) {
        return ApiResult.error("Something went wrong: " + exception.getMessage());
    }

}
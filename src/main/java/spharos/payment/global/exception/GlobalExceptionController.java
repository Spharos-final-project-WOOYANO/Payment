package spharos.payment.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<ErrorResponse> customExHandle(CustomException e) {
        log.error("[exceptionHandle] ex", e);
        return ErrorResponse.toResponseEntity(e.getResponseCode());
    }


}

package spharos.payment.global.exception;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {

    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(responseCode.getHttpStatus().value())
                        .error(responseCode.getHttpStatus().name())
                        .code(responseCode.name())
                        .message(responseCode.getDetail())
                        .build()
                );
    }
}

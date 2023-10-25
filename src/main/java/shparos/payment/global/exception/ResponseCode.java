package shparos.payment.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    OK(HttpStatus.OK, "성공"),
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "없는 결제정보입니다");


    private final HttpStatus httpStatus;
    private final String detail;
}

package spharos.payment.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK,true, 200, "요청에 성공하였습니다."),


    /**
     * 에러 코드
     **/
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST,true, 3010, "결제 정보가 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final boolean success;
    private final int code;
    private final String message;

}

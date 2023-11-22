package spharos.payment.global.utill;

import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public static String getRandomValue() {
        //↓랜덤으로 문자열 생성
        StringBuffer strPwd = new StringBuffer();

        //랜덤 문자+숫자 조합
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            //↓ rnd.nextBoolean()으로 값이 true or false로 무작위로
            //  바뀌면서 true라면 문자 false면 숫자가 생성됨
            if (rnd.nextBoolean()) {
                //소문자
                strPwd.append((char) ((int) (rnd.nextInt(26)) + 97));
            } else {
                //0~9 숫자
                strPwd.append((rnd.nextInt(10)));
            }
        }
        return strPwd.toString();
    }

}

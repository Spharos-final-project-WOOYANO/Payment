package shparos.payment.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import shparos.payment.dto.PaymentResultResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventsProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, PaymentResultResponse> DTOKafkaTemplate;

    @Value("${spring.kafka.topic}")
    public String topic;


    public CompletableFuture<SendResult<String, String>> sendLibraryEvent(PaymentResultResponse list)
            throws JsonProcessingException {
        log.info("Sending Monthly Payment Event");
        var key = "test";
        String value = objectMapper.writeValueAsString(list);
        //send하면 2개
        //1.blocking call- kafka cluster에 대한 메타데이터를 가져온다 - 이게실패하면 메세지 못보냄
        //2.메세지 보내기가 실제로 발생하고 비동기 반환 send message happens - return a completableFuture
        log.info("value : {}", value);
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send("test-events",null, value);
        return send
                .whenComplete((sendResult, throwable) -> {
                    if (throwable != null) {
                        handleFailure(key, value, throwable); // 에러처리
                    } else {
                        handleSuccess(key, value, sendResult);

                    }
                });
    }
/*
    private ProducerRecord<Integer, String> buildProducerRecord(String key, String value) {
       // List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));  //바이트 배열을 제공
        return new ProducerRecord<>(key,value);
    }
*/

    private void handleFailure(String key, String value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
//        try {
//            throw ex;
//        } catch (Throwable throwable) {
//            log.error("Error in OnFailure: {}", throwable.getMessage());
//        }

    }
    private void handleSuccess(String key, String value, SendResult<String, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
                key, value, result.getRecordMetadata().partition());
    }
}

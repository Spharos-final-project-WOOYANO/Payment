package spharos.payment.batch;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spharos.payment.dto.BatchPayment;


@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentWriter implements ItemWriter<BatchPayment> {

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Integer> valusOps;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public void write(Chunk<? extends BatchPayment> chunk) throws Exception {
//        for(BatchPayment batchPayment : chunk){
//            System.out.println("item = " + batchPayment);
//            String clientEmail = batchPayment.getClientEmail();
//            int totalAmount = batchPayment.getTotalAmount();
//            valusOps.increment(clientEmail, totalAmount);
//            log.info("end writer");
//        }
        log.info("chunk : {}", chunk);

    }
}

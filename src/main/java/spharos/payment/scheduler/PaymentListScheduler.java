package spharos.payment.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spharos.payment.application.PaymentService;
import spharos.payment.dto.PaymentResultResponse;
import spharos.payment.producer.PaymentEventsProducer;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentListScheduler {

   private final PaymentService paymentService;
   private final PaymentEventsProducer paymentEventsProducer;

    @Scheduled(cron = "0 0 2 * * ?")
    public void sendMonthlyPaymentEvent() throws JsonProcessingException {
        log.info("Sending Monthly Payment Event");
        List<PaymentResultResponse> paymentsList = paymentService.getPaymentsList();
        for (PaymentResultResponse PaymentResultResponse : paymentsList) {
            paymentEventsProducer.sendLibraryEvent(PaymentResultResponse);
        }
        log.info("paymentsList : {}", paymentsList);
       // paymentEventsProducer.sendLibraryEvent(paymentsList);
    }
}

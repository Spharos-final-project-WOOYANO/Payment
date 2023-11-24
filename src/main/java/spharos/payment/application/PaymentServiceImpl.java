package spharos.payment.application;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import spharos.payment.axon.command.SavePaymentCommand;
import spharos.payment.domain.Payment;
import spharos.payment.domain.PaymentStatus;
import spharos.payment.domain.PaymentType;
import spharos.payment.dto.PaymentRequest;
import spharos.payment.dto.PaymentResultResponse;
import spharos.payment.infrastructure.PaymentRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl  {

    private final PaymentRepository paymentRepository;
    private final CommandGateway commandGateway;

    //결제 저장
    public void savePayment(PaymentRequest paymentRequest) {
   /*     Payment payment = Payment.createPayment(paymentRequest.getClientEmail(),  paymentRequest.getPayType(),
                paymentRequest.getTotalAmount(), paymentRequest.getApprovedAt(),paymentRequest.getPayStatus());
        paymentRepository.save(payment);*/
        SavePaymentCommand savePaymentCommand = new SavePaymentCommand(UUID.randomUUID().toString(),
                paymentRequest.getClientEmail(),
                paymentRequest.getPayType(), paymentRequest.getTotalAmount(), paymentRequest.getApprovedAt(),
                paymentRequest.getPayStatus());
        commandGateway.send(savePaymentCommand);
    }
    //결제 완료,취소 리스트
    public List<PaymentResultResponse> getPaymentsList(){
        LocalDate requestDate = LocalDate.now();

        LocalDateTime parse = LocalDateTime.parse(requestDate + "T00:00:00");
        LocalDateTime parse1 = LocalDateTime.parse(requestDate + "T23:59:58");

        log.info("firstDayOfMonth : {}", parse);
        log.info("lastDayOfMonth : {}", parse1);
        var doneStatus = PaymentStatus.DONE;
        var cancelStatus = PaymentStatus.CANCEL;

        List<PaymentResultResponse> payments = paymentRepository.findByApprovedAtAndPaymentStatus(parse, parse1);
        return payments;

    }


}

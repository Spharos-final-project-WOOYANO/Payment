package shparos.payment.application;

import static shparos.payment.domain.PaymentStatus.SETTLED;
import static shparos.payment.global.exception.ResponseCode.PAYMENT_NOT_FOUND;


import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shparos.payment.domain.Payment;
import shparos.payment.domain.PaymentStatus;
import shparos.payment.dto.FinishSettlementRequest;
import shparos.payment.dto.PaymentRequest;
import shparos.payment.dto.PaymentResultResponse;
import shparos.payment.global.exception.CustomException;
import shparos.payment.global.exception.ResponseCode;
import shparos.payment.repository.PaymentRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
   // private final CommandGateway commandGateway;

    //결제 저장
    public void savePayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.createPayment(paymentRequest.getClientEmail(),  paymentRequest.getPayType(),
                paymentRequest.getTotalAmount(), paymentRequest.getApprovedAt(),paymentRequest.getPayStatus());
        paymentRepository.save(payment);
    }
    //결제 완료,취소 리스트
    public List<PaymentResultResponse> getPaymentsList(){
        String requestDate = "2023-11-09";

        LocalDateTime parse = LocalDateTime.parse(requestDate + "T00:00:00");
        LocalDateTime parse1 = LocalDateTime.parse(requestDate + "T23:59:58");

/*        var now = LocalDateTime.now();
        var firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        var lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());*/

        log.info("firstDayOfMonth : {}", parse);
        log.info("lastDayOfMonth : {}", parse1);
        var doneStatus = PaymentStatus.DONE;
        var cancelStatus = PaymentStatus.CANCEL;

        List<PaymentResultResponse> payments = paymentRepository.findByApprovedAtAndPaymentStatus(parse, parse1);
        return payments;
        /*return payments.stream()
                .map(payment -> {
                    PaymentResultResponse response = new PaymentResultResponse();
                    response.setClientEmail(payment.getClientEmail());
                    response.setTotalAmount(payment.getTotalAmount());
                    return response;
                })
                .collect(Collectors.toList());*/
    }

    //결재 상태 정산으로 변경
    @Transactional
    public void finishSettlement(FinishSettlementRequest finishSettlementRequest){
        Payment payment = paymentRepository.findById(finishSettlementRequest.getId())
                .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND));
        payment.finishSettlement(SETTLED);
    }

/*    public void test(PaymentRequest command){
        TestCommand testCommand = new TestCommand(command.getClientEmail());
        commandGateway.send(testCommand);
    }*/
}

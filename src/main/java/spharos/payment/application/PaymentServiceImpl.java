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
import spharos.payment.batch.BatchScheduler;
import spharos.payment.domain.enumPackage.PaymentStatus;
import spharos.payment.dto.PaymentRequest;
import spharos.payment.dto.PaymentResultResponse;
import spharos.payment.infrastructure.PaymentRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CommandGateway commandGateway;
    private final BatchScheduler batchScheduler;

    //결제 저장
    public void savePayment(PaymentRequest paymentRequest) {
        SavePaymentCommand savePaymentCommand = new SavePaymentCommand(UUID.randomUUID().toString(),
                paymentRequest.getClientEmail(),
                paymentRequest.getPayType(), paymentRequest.getTotalAmount(), paymentRequest.getApprovedAt(),
                paymentRequest.getPayStatus());
        commandGateway.send(savePaymentCommand);
    }
    //결제 완료,취소 리스트
    public List<PaymentResultResponse> getPaymentsList(){
        LocalDate requestDate = LocalDate.now();

        LocalDateTime startTime = LocalDateTime.parse(requestDate + "T00:00:00");
        LocalDateTime endTime = LocalDateTime.parse(requestDate + "T23:59:58");

        List<PaymentResultResponse> payments = paymentRepository.findByApprovedAtAndPaymentStatus(startTime, endTime);
        return payments;

    }

    //배치 하고 db에서 이름만 뽑아서 이름으로 밸류 찾고 그걸 카프카로 보내보기
    public void batchTest(){
        batchScheduler.runJob();

    }


}

package shparos.payment.application;

import static shparos.payment.domain.PaymentStatus.SETTLED;
import static shparos.payment.global.exception.ResponseCode.PAYMENT_NOT_FOUND;


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
import shparos.payment.dto.PaymentResultResponseList;
import shparos.payment.global.exception.CustomException;
import shparos.payment.global.exception.ResponseCode;
import shparos.payment.repository.PaymentRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    //결제 저장
    public void savePayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.createPayment(paymentRequest.getClientEmail(),  paymentRequest.getPayType(),
                paymentRequest.getTotalAmount(), paymentRequest.getApprovedAt(),paymentRequest.getPayStatus());
        paymentRepository.save(payment);
    }
    //결제 완료,취소 리스트
    public List<PaymentResultResponseList> getPaymentsList(){

        PaymentStatus doneStatus = PaymentStatus.DONE;
        PaymentStatus cancelStatus = PaymentStatus.CANCEL;

        List<Payment> payments = paymentRepository.findByPaymentStatus(doneStatus, cancelStatus);
        return payments.stream()
                .map(payment -> {
                    PaymentResultResponseList response = new PaymentResultResponseList();
                    response.setClientEmail(payment.getClientEmail());
                    response.setTotalAmount(payment.getTotalAmount());
                    response.setApprovedAt(payment.getApprovedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }

    //결재 상태 정산으로 변경
    @Transactional
    public void finishSettlement(FinishSettlementRequest finishSettlementRequest){
        Payment payment = paymentRepository.findById(finishSettlementRequest.getId())
                .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND));
        payment.finishSettlement(SETTLED);
    }
}

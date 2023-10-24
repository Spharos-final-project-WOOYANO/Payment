package shparos.payment.domain;

public enum PayStatus {
    DONE("결제 완료"),
    CANCEL("결제 취소");
    private String description;

    PayStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

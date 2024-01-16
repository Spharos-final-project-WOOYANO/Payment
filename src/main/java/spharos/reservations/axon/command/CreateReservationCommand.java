package spharos.reservations.axon.command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationCommand {

    private String orderId;
    private int amount;
    private Long serviceId;
    private String userEmail;
    private LocalDate reservationDate;
    private String request;
    private String address;
    private LocalTime serviceStart;
    private Long workerId;


}

/*
package shparos.payment.axon.agegregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.jetbrains.annotations.NotNull;
import shparos.payment.axon.command.TestCommand;
import shparos.payment.axon.event.TestEvent;

@Aggregate()
@Data
public class TestAggregate {

    @AggregateIdentifier
    private String id;

    private String clientEmail;

    @CommandHandler
    public TestAggregate(@NotNull TestCommand command) {
        System.out.println("command = " + command);

        apply(new TestEvent(command.getClientEmail()));
    }

    @EventSourcingHandler
    public void on(TestEvent event) {
        System.out.println("event = " + event);
        id = UUID.randomUUID().toString();
        clientEmail = event.getMembershipId();
    }

    public TestAggregate() {
    }
}
*/

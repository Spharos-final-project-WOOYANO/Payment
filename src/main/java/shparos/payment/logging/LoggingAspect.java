package shparos.payment.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private final LoggingProducer loggingProducer;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    public LoggingAspect(LoggingProducer loggingProducer) {
        this.loggingProducer = loggingProducer;
    }

    @Before("execution(* shparos.payment.presentation.PaymentController.*(..))")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String logMessage = "Application: " + applicationName + ", Method: " + methodName;
        loggingProducer.sendMessage("logging", logMessage);
        // Produce Access log
        //cluster에 바로 produce
    }
}
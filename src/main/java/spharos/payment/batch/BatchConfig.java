package spharos.payment.batch;

import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import spharos.payment.domain.Payment;
import spharos.payment.dto.BatchPayment;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BatchConfig {
    private final EntityManagerFactory entityManagerFactory;

    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final PaymentWriter paymentWriter;
    private final static int CHUNK_SIZE = 500;
    @Bean
    public Job createJob() {
        return new JobBuilder("paymentJob", jobRepository)
                //     .validator(new CustomJobParameterValidator())
                .start(paymentStep())
                .build();
    }

    @Bean
    public Step paymentStep() {
        return new StepBuilder("paymentStep", jobRepository)
                .<BatchPayment, BatchPayment>chunk(CHUNK_SIZE,transactionManager) // Chunk 크기를 지정
                .reader(reader())
                //   .processor(paymentItemProcessor)
                .writer(paymentWriter)
//                .allowStartIfComplete(true) // 스텝을 완료 상태에서 다시 시작할 수 있도록 설정

                .build();

    }

    @Bean
    @StepScope
    public JpaPagingItemReader<BatchPayment> reader() {

        LocalDate requestDate = LocalDate.now();
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("startDateTime", LocalDateTime.parse(requestDate + "T00:00:00"));
        parameters.put("endDateTime", LocalDateTime.parse(requestDate + "T23:59:59"));


        String queryString = String.format("select new %s(p.clientEmail, p.totalAmount ) From Payment p "
                + "where p.approvedAt between :startDateTime and :endDateTime", BatchPayment.class.getName());

        JpaPagingItemReaderBuilder<BatchPayment> jpaPagingItemReaderBuilder  = new JpaPagingItemReaderBuilder<>();
        JpaPagingItemReader<BatchPayment> paymentItemReader = jpaPagingItemReaderBuilder
                .name("paymentItemReader")
                .entityManagerFactory(entityManagerFactory) //readerEntityManagerFactory.getObject()
                .parameterValues(parameters)
                .queryString(queryString)
                .pageSize(10)
                .build();
        log.info("reader={}",paymentItemReader.toString());
        return paymentItemReader;
    }


}

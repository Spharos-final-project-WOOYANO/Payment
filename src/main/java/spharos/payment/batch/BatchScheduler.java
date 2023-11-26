package spharos.payment.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private BatchConfig batchConfig;

    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(batchConfig.createJob(), jobParameters);

            // Job 실행 결과 출력
            System.out.println("Job Execution Status: " + jobExecution.getStatus());
            System.out.println("Job ID: " + jobExecution.getJobId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

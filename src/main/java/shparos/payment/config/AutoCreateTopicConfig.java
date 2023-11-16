package shparos.payment.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
//@Profile("local")
public class AutoCreateTopicConfig {


    //토픽생성
    @Bean
    public NewTopic libraryEvents(){
        return TopicBuilder.name("test-events")
                .partitions(3)

                .build();
    }

}
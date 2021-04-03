/**
 * @author anthonydonx
 */
package lk.techlearnlab.sqs_example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration // annotation tells that the class contain @Bean
public class SQSConfig {
    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    // tells this method produce bean
    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(){
        return new QueueMessagingTemplate(getSQSAsync());
    }
    @Primary
    @Bean
    public AmazonSQSAsync getSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
    }
}

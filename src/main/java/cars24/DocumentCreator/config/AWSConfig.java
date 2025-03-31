package cars24.DocumentCreator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Slf4j
public class AWSConfig {

//    @Value("${aws.region}")
//    private String region;
//
//    @Value("${aws.access.key.id}")
//    private String accessKeyId;
//
//    @Value("${aws.secret.access.key}")
//    private String secretAccessKey;

//    @Bean
//    public S3Client s3Client() {
//        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
//                accessKeyId.trim(),      // added trim() to remove any accidental spaces
//                secretAccessKey.trim()
//        );
//        return S3Client.builder()
//                .region(Region.AP_SOUTH_1)  // make sure this matches your bucket's region
//                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
//                .build();
//    }
}

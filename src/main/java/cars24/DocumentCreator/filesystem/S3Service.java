package cars24.DocumentCreator.filesystem;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.net.URL;

public interface S3Service {

    URI uploadFile(MultipartFile file, String key, String bucketName);
    String copyS3Object(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey);
    String copyS3ObjectInSameBucket(String sourceKey, String destinationKey, String bucketName);
    void deleteFileByKey(String key, String bucketName);
    File downloadFile(String key, String bucketName);
    URL generatePresignedUrl(String key, long expTimeMillis, String bucketName);
    URI uploadFile(File file, String key, String bucketName);
}

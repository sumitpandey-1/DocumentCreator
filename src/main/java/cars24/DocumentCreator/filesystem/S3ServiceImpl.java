//package cars24.DocumentCreator.filesystem;
//import cars24.DocumentCreator.enums.S3Operation;
//import cars24.DocumentCreator.exceptions.S3OperationException;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.*;
//import software.amazon.awssdk.services.s3.presigner.S3Presigner;
//import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
//
//import java.io.File;
//import java.net.URI;
//import java.net.URL;
//import java.time.Duration;
//
//@Service
//@Slf4j
//@AllArgsConstructor
//public class S3ServiceImpl implements S3Service {
//
//    private final S3Client s3Client;
//
//    @Value("${S3_BUCKET_NAME}")
//    private String bucketName;
//
//    private PutObjectRequest buildPutObjectRequest(String key, String contentType, Long contentLength, String bucketName) {
//        PutObjectRequest.Builder builder = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(bucketName + key)
//                .contentType(contentType);
//        if (contentLength != null) {
//            builder.contentLength(contentLength);
//        }
//        return builder.build();
//    }
//
//    private GetObjectRequest buildGetObjectRequest(String key , String bucketName) {
//        return GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(bucketName + key)
//                .build();
//    }
//
//    private DeleteObjectRequest buildDeleteObjectRequest(String key, String bucketName) {
//        return DeleteObjectRequest.builder()
//                .bucket(bucketName)
//                .key(bucketName + key)
//                .build();
//    }
//
//    private CopyObjectRequest buildCopyObjectRequest(String sourceKey, String destinationKey, String sourceBucket, String destinationBucket) {
//        return CopyObjectRequest.builder()
//                .sourceBucket(sourceBucket)
//                .sourceKey(sourceKey)
//                .destinationBucket(destinationBucket)
//                .destinationKey(destinationKey)
//                .build();
//    }
//
//    @Override
//    public URI uploadFile(MultipartFile file, String key, String bucketName) {
//        logOperation(S3Operation.UPLOAD, file.getName());
//        try {
//            PutObjectRequest request = buildPutObjectRequest(
//                    key,
//                    file.getContentType(),
//                    file.getSize(),
//                    bucketName
//            );
//            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
//            logSuccess(S3Operation.UPLOAD, file.getName(), key);
//            return URI.create(createSavedPath(key));
//        } catch (Exception e) {
//            logError(S3Operation.UPLOAD.getName(), key, e);
//            throw new S3OperationException("Failed to upload file to S3- " + e.getMessage());
//        }
//    }
//
//    @Override
//    public URI uploadFile(File file, String key, String bucketName) {
//        logOperation(S3Operation.UPLOAD, file.getName());
//        try {
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(bucketName + key)
//                    .build();
//
//            s3Client.putObject(request, file.toPath());
//            logSuccess(S3Operation.UPLOAD, file.getName(), key);
//            return URI.create(createSavedPath(key));
//        } catch (Exception e) {
//            logError(S3Operation.UPLOAD.getName(), key, e);
//            throw new S3OperationException("Failed to upload file to S3- " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteFileByKey(String key, String bucketName) {
//        logOperation(S3Operation.DELETE, key);
//        try {
//            DeleteObjectRequest request = buildDeleteObjectRequest(key, bucketName);
//            s3Client.deleteObject(request);
//            logSuccess(S3Operation.DELETE, key);
//        } catch (S3Exception e) {
//            logError(S3Operation.DELETE.getName(), key, e);
//        }
//    }
//
//    @Override
//    public File downloadFile(String key , String bucketName) {
////        if (key == null) {
////            return null;
////        }
////        logOperation(S3Operation.DOWNLOAD, key);
////        try {
////            GetObjectRequest request = buildGetObjectRequest(key, bucketName);
////            ResponseInputStream<GetObjectResponse> objectContent = s3Client.getObject(request);
////            File tempFile = File.createTempFile("tempVideo", FilenameUtils.getName(key));
////            //File tempFile = createFile(key, null);
////            FileUtils.copyInputStreamToFile(objectContent, tempFile);
////            logSuccess(S3Operation.DOWNLOAD, key);
////            return tempFile;
////        } catch (IOException e) {
////            logError(S3Operation.DOWNLOAD.getName(), key, e);
////            return null;
////        }
//        return null;
//    }
//
//    @Override
//    public URL generatePresignedUrl(String key, long expTimeMillis, String bucketName) {
//        try (S3Presigner presigner = createPresigner()) {
//            GetObjectPresignRequest presignRequest = createPresignRequest(key, expTimeMillis, bucketName);
//            return presigner.presignGetObject(presignRequest).url();
//        }
//    }
//
//    @Override
//    public String copyS3Object(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {
//        return getCopiedObjectKey(sourceKey, destinationKey, sourceBucket, destinationBucket);
//    }
//
//    @Override
//    public String copyS3ObjectInSameBucket(String sourceKey, String destinationKey, String bucketName) {
//        return getCopiedObjectKey(sourceKey, destinationKey, bucketName, bucketName);
//    }
//
//    // Helper methods
//    private String getCopiedObjectKey(String sourceKey, String destinationKey, String sourceBucket, String destinationBucket) {
//        logOperation(S3Operation.COPY, sourceKey, destinationKey);
//        try {
//            CopyObjectRequest request = buildCopyObjectRequest(sourceKey, destinationKey, sourceBucket, destinationBucket);
//            s3Client.copyObject(request);
//            logSuccess(S3Operation.COPY, sourceKey, destinationKey);
//            return destinationKey;
//        } catch (S3Exception e) {
//            logError(S3Operation.COPY.getName(), sourceKey + " to " + destinationKey, e);
//            throw new S3OperationException("Error copying S3 object- " + e.getMessage());
//        }
//    }
//
//    private S3Presigner createPresigner() {
//        return S3Presigner.builder()
//                .region(s3Client.serviceClientConfiguration().region())
//                .build();
//    }
//
//    private GetObjectPresignRequest createPresignRequest(String key, long expTimeMillis, String bucketName) {
//        return GetObjectPresignRequest.builder()
//                .signatureDuration(Duration.ofMillis(expTimeMillis))
//                .getObjectRequest(buildGetObjectRequest(key, bucketName))
//                .build();
//    }
//
//    private String createSavedPath(String key) {
//        return String.format("s3://%s/%s", bucketName, key);
//    }
//
//    private void logOperation(S3Operation operation, Object... params) {
//        log.info(operation.getLogMessage(), params);
//    }
//
//    private void logError(String operation, String key, Exception e) {
//        log.error("Error during {} operation for key: {}", operation, key, e);
//    }
//
//    private void logSuccess(S3Operation operation, Object... params) {
//        log.info(operation.getLogMessage() + " - Completed", params);
//    }
//}

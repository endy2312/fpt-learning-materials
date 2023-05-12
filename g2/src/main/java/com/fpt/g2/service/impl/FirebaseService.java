//package com.fpt.g2.service.impl;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class FirebaseService {
//    private Storage storage;
//
//    @EventListener
//    public void init(ApplicationReadyEvent event) {
//        try {
//            ClassPathResource serviceAccount = new ClassPathResource("firebase.json");
//            storage = StorageOptions.newBuilder().
//                    setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).
//                    setProjectId("YOUR_PROJECT_ID").build().getService();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public String saveTest(MultipartFile file) throws IOException {
//        String imageName = generateFileName(file.getOriginalFilename());
//        Map<String, String> map = new HashMap<>();
//        map.put("firebaseStorageDownloadTokens", imageName);
//        BlobId blobId = BlobId.of("YOUR_BUCKET_NAME", imageName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
//                .setMetadata(map)
//                .setContentType(file.getContentType())
//                .build();
//        storage.create(blobInfo, file.getInputStream());
//        return imageName;
//    }
//
//    private String generateFileName(String originalFileName) {
//        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
//    }
//
//    private String getExtension(String originalFileName) {
//        return StringUtils.getFilenameExtension(originalFileName);
//    }
//}

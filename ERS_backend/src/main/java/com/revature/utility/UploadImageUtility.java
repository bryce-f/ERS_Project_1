package com.revature.utility;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import io.javalin.http.UploadedFile;

import java.io.FileInputStream;
import java.io.IOException;


public class UploadImageUtility {
    private static final String bucketName = "project-1-bucket-bf";

    // Solution by Mani on StackOverflow
    //https://stackoverflow.com/questions/42893395/upload-image-to-google-cloud-storage-java
    public static String uploadImage(UploadedFile uploadedFile) {
        Bucket bucket = getBucket(bucketName);
        Blob blob = bucket.create("reimb-images/" + uploadedFile.getFilename(), uploadedFile.getContent(), uploadedFile.getContentType());

        return "https://storage.googleapis.com/project-1-bucket-bf/" + blob.getName();
    }

    private static Bucket getBucket(String bucketName) {
        try{
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS")))
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            Bucket bucket = storage.get(bucketName);
            if (bucket == null) {
                throw new IOException("Bucket not found:"+bucketName);
            }
            return bucket;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
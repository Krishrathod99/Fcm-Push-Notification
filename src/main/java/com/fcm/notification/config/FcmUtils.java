package com.fcm.notification.config;

import com.fcm.notification.constants.MessageConstants;
import com.fcm.notification.constants.ServiceConstants;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * The type Fcm utils.
 */
@Component
public class FcmUtils {
    /**
     * The Fcm tokens.
     */
    public List<String> fcmTokens = new ArrayList<>();

    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(FcmUtils.class);

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {
        try {
            System.out.println(firebaseConfigPath != null ? "Credentials loaded" : "Credentials missing");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info(MessageConstants.FIREBASE_APPLICATION_INITIALIZED_SUCCESS);
            }
            loadFcmTokens();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFcmTokens() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(ServiceConstants.FIREBASE_DB_COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        fcmTokens = documents.stream()
                .map(doc -> doc.getString(ServiceConstants.FCM_TOKEN))
                .toList();
    }


}

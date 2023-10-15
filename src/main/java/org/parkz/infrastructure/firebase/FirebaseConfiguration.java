package org.parkz.infrastructure.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(FirebaseConfigurationProperties.class)
public class FirebaseConfiguration {

    private final FirebaseConfigurationProperties properties;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(properties.getConfigPath())) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(fileInputStream))
                    .build();
            return FirebaseApp.initializeApp(options);
        }
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @PostConstruct
    public void test() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(properties.getConfigPath())) {
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(fileInputStream))
                    .build()
                    .getService();
            Bucket defaultBucket = storage.get("parkx-22f01.appspot.com");

            Cors cors =
                    Cors.newBuilder()
                            .setOrigins(List.of(Cors.Origin.of("*")))
                            .setMethods(List.of(HttpMethod.GET, HttpMethod.PUT))
                            .setResponseHeaders(List.of("*"))
                            .setMaxAgeSeconds(3600)
                            .build();
            defaultBucket.toBuilder().setCors(List.of(cors)).build().update();
        }

    }
}

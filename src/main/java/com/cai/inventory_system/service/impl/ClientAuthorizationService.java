//package com.cai.inventory_system.service.impl;
//
//import com.cai.inventory_system.client.AuthorizationApiClient;
//import com.cai.inventory_system.model.request.AuthenticationRequest;
//import com.cai.inventory_system.model.response.ApiResponse;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.Timer;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import java.io.IOException;
//
//@Service
//@Slf4j
//public class ClientAuthorizationService {
//
//    private final String endpoint;
//    private final String apiKey;
//    private final String contentType;
//    private final AuthorizationApiClient apiClient;
//    private final MeterRegistry meterRegistry;
//
//    public ClientAuthorizationService(
//            @Value("${authorization.endpoint}") String endpoint,
//            @Value("${authorization.api.key}") String apiKey,
//            MeterRegistry meterRegistry) {
//
//        this.endpoint = endpoint;
//        this.apiKey = apiKey;
//        this.contentType = "application/json";
//        this.meterRegistry = meterRegistry;
//
//        // Initialize Retrofit client
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(this.endpoint)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        this.apiClient = retrofit.create(AuthorizationApiClient.class);
//
//        log.info("ClientAuthorizationService initialized with endpoint: {}", this.endpoint);
//    }
//
//    public ApiResponse<Boolean> authorize(String method, String attemptingServiceId) {
//        // Create authentication request
//        AuthenticationRequest request = new AuthenticationRequest(
//                method,
//                attemptingServiceId,
//                this.apiKey
//        );
//
//        // Measure time with metrics
//        Timer.Sample sample = Timer.start(meterRegistry);
//
//        try {
//            Response<Boolean> response = apiClient.authenticate("authenticate", request).execute();
//
//            // Record metrics
//            sample.stop(Timer.builder("client.authorization.service.authenticate")
//                    .tag("method", method)
//                    .tag("status", String.valueOf(response.code()))
//                    .register(meterRegistry));
//
//            // Handle response
//            if (response.isSuccessful() && response.code() == 200) {
//                Boolean authResult = response.body();
//                log.info("ClientAuthorizationService.AuthenticateSuccess - StatusCode: {}, " +
//                        "Successfully Authenticated for method: {}, Attempting Service ID: {}, " +
//                        "GoalServiceKey: {}",
//                        response.code(), method, attemptingServiceId, apiKey);
//
//                return ApiResponse.success(authResult, response.code());
//            } else {
//                log.error("ClientAuthorizationService.AuthenticateFailure - StatusCode: {}, " +
//                        "Failed to Authenticate for method: {}, Attempting Service ID: {}, " +
//                        "GoalServiceKey: {}",
//                        response.code(), method, attemptingServiceId, apiKey);
//
//                return ApiResponse.error(
//                        response.code(),
//                        "Authentication failed: " + response.message()
//                );
//            }
//
//        } catch (IOException e) {
//            log.error("ClientAuthorizationService.AuthenticateFailure - Network error for " +
//                    "method: {}, Attempting Service ID: {}", method, attemptingServiceId, e);
//
//            // Record error metrics
//            sample.stop(Timer.builder("client.authorization.service.authenticate")
//                    .tag("method", method)
//                    .tag("status", "error")
//                    .register(meterRegistry));
//
//            return ApiResponse.error(500, "Unable to reach authorization service", e);
//
//        } catch (Exception e) {
//            log.error("ClientAuthorizationService.AuthenticateFailure - Unexpected error for " +
//                    "method: {}, Attempting Service ID: {}", method, attemptingServiceId, e);
//
//            // Record error metrics
//            sample.stop(Timer.builder("client.authorization.service.authenticate")
//                    .tag("method", method)
//                    .tag("status", "exception")
//                    .register(meterRegistry));
//
//            return ApiResponse.error(500, "Internal error during authorization", e);
//        }
//    }
//}

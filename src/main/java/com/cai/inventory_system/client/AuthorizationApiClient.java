package com.cai.inventory_system.client;

import com.cai.inventory_system.model.request.AuthenticationRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthorizationApiClient {

    @POST("clientauthorization")
    Call<Boolean> authenticate(
        @Query("action") String action,
        @Body AuthenticationRequest request
    );
}

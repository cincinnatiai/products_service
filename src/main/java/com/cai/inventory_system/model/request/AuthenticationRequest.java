package com.cai.inventory_system.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request model for client authorization.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    @SerializedName("method")
    private String method;

    @SerializedName("attempting_service_id")
    private String attemptingServiceId;

    @SerializedName("goal_service_key")
    private String goalServiceKey;
}

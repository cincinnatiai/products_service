package com.cai.inventory_system.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Generic API response wrapper for external API calls.
 * Similar to Go's response.Response[T] type.
 *
 * @param <T> The type of data contained in the response
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private T data;
    private Integer statusCode;
    private String message;
    private Throwable error;

    /**
     * Creates a successful response with data
     */
    public static <T> ApiResponse<T> success(T data, int statusCode) {
        return new ApiResponse<>(data, statusCode, null, null);
    }

    /**
     * Creates an error response
     */
    public static <T> ApiResponse<T> error(int statusCode, String message, Throwable error) {
        return new ApiResponse<>(null, statusCode, message, error);
    }

    /**
     * Creates an error response without throwable
     */
    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return new ApiResponse<>(null, statusCode, message, null);
    }

    /**
     * Checks if the response is successful (status code 200-299)
     */
    public boolean isSuccessful() {
        return statusCode != null && statusCode >= 200 && statusCode < 300;
    }
}

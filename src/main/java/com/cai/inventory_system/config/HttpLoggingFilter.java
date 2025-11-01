package com.cai.inventory_system.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpLoggingFilter implements Filter {

    private final Tracer tracer;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);

        long startTime = System.currentTimeMillis();

        try {
            // Log request
            logRequest(requestWrapper);

            // Continue with the filter chain
            chain.doFilter(requestWrapper, responseWrapper);

        } finally {
            long duration = System.currentTimeMillis() - startTime;

            // Log response
            logResponse(responseWrapper, duration);

            // Copy the response body back to the original response
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String traceId = getTraceId();
        String spanId = getSpanId();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n========== HTTP REQUEST ==========\n");
        logMessage.append("TraceId: ").append(traceId).append("\n");
        logMessage.append("SpanId: ").append(spanId).append("\n");
        logMessage.append("Method: ").append(request.getMethod()).append("\n");
        logMessage.append("URI: ").append(request.getRequestURI()).append("\n");

        String queryString = request.getQueryString();
        if (queryString != null) {
            logMessage.append("Query String: ").append(queryString).append("\n");
        }

        logMessage.append("Remote Address: ").append(request.getRemoteAddr()).append("\n");

        // Log headers
        logMessage.append("Headers: \n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            // Mask sensitive headers
            if (headerName.toLowerCase().contains("authorization") ||
                headerName.toLowerCase().contains("password") ||
                headerName.toLowerCase().contains("token")) {
                headerValue = "***MASKED***";
            }
            logMessage.append("  ").append(headerName).append(": ").append(headerValue).append("\n");
        }

        // Log request body (if present)
        String requestBody = getRequestBody(request);
        if (requestBody != null && !requestBody.isEmpty()) {
            logMessage.append("Request Body: ").append(requestBody).append("\n");
        }

        logMessage.append("===================================");

        log.info(logMessage.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) {
        String traceId = getTraceId();
        String spanId = getSpanId();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n========== HTTP RESPONSE ==========\n");
        logMessage.append("TraceId: ").append(traceId).append("\n");
        logMessage.append("SpanId: ").append(spanId).append("\n");
        logMessage.append("Status: ").append(response.getStatus()).append("\n");
        logMessage.append("Duration: ").append(duration).append(" ms\n");

        // Log response headers
        logMessage.append("Headers: \n");
        for (String headerName : response.getHeaderNames()) {
            logMessage.append("  ").append(headerName).append(": ")
                      .append(response.getHeader(headerName)).append("\n");
        }

        // Log response body
        String responseBody = getResponseBody(response);
        if (responseBody != null && !responseBody.isEmpty()) {
            logMessage.append("Response Body: ").append(responseBody).append("\n");
        }

        logMessage.append("===================================");

        log.info(logMessage.toString());
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            try {
                return new String(content, request.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                log.warn("Failed to parse request body", e);
            }
        }
        return null;
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            try {
                return new String(content, response.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                log.warn("Failed to parse response body", e);
            }
        }
        return null;
    }

    private String getTraceId() {
        Span currentSpan = tracer.currentSpan();
        if (currentSpan != null && currentSpan.context() != null) {
            return currentSpan.context().traceId();
        }
        return "N/A";
    }

    private String getSpanId() {
        Span currentSpan = tracer.currentSpan();
        if (currentSpan != null && currentSpan.context() != null) {
            return currentSpan.context().spanId();
        }
        return "N/A";
    }
}

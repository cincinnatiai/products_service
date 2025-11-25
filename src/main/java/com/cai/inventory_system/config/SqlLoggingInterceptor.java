package com.cai.inventory_system.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqlLoggingInterceptor implements Interceptor {

    private final Tracer tracer;

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        logSqlOperation("INSERT", entity.getClass().getSimpleName(), id);
        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState,
                                String[] propertyNames, Type[] types) throws CallbackException {
        logSqlOperation("UPDATE", entity.getClass().getSimpleName(), id);
        return false;
    }

    @Override
    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException {
        logSqlOperation("DELETE", entity.getClass().getSimpleName(), id);
    }

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException {
        logSqlOperation("SELECT", entity.getClass().getSimpleName(), id);
        return false;
    }

    private void logSqlOperation(String operation, String entityName, Object id) {
        String traceId = getTraceId();
        String spanId = getSpanId();

        log.debug("SQL Operation - TraceId: {}, SpanId: {}, Operation: {}, Entity: {}, ID: {}",
                traceId, spanId, operation, entityName, id);
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

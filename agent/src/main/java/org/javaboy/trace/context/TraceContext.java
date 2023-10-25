package org.javaboy.trace.context;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;


@Data
/**
 * @author 码劲
 */
public class TraceContext {
    private String traceId;
    private String parentSpanId;
    private AtomicInteger sequence = new AtomicInteger(1);

}

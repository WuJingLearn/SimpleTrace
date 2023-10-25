package org.javaboy.trace.context;

/**
 * @author 码劲
 */
public class ServerContext {

    private static final ThreadLocal<TraceContext> TRACE_CONTEXT = new ThreadLocal<>();

    public static ServerContext INSTANCE = new ServerContext();

    private ServerContext() {

    }

    public TraceContext getTraceContext() {
        return TRACE_CONTEXT.get();
    }

    public void setTraceContext(TraceContext context) {
        TRACE_CONTEXT.set(context);
    }

    public void clearTraceContext() {
        TRACE_CONTEXT.remove();
    }

    public String nextSpanId() {
        TraceContext traceContext = TRACE_CONTEXT.get();
        if (traceContext != null) {
            return traceContext.getParentSpanId()+"."+traceContext.getSequence().getAndIncrement();
        }
        return null;
    }


}

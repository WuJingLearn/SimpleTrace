package org.javaboy.trace.enhence;

import net.bytebuddy.implementation.bind.annotation.*;
import org.apache.dubbo.rpc.RpcContext;
import org.javaboy.trace.collect.Collector;
import org.javaboy.trace.context.ServerContext;
import org.javaboy.trace.context.TraceContext;
import org.javaboy.trace.model.Span;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;
/**
 * 如果是在服务端，那么在服务端中发起的两个rpc调用，使用的是同一个traceId。
 * 如果在没有rpc上下文中，发起的两次rpc调用,这两次是单独的trace
 */

/**
 * @author 码劲
 */
public class DubboInterceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable, @AllArguments Object[] allArguments, @This Object obj1, @Super Object obj2) throws Exception {

        RpcContext context = RpcContext.getContext();
        boolean isConsumer = context.isConsumerSide();
        if (isConsumer) {
            String curSpanId = null;
            //向下游传递的父spanId
            String parentSpanId = null;
            String traceId = null;
            ServerContext serverContext = ServerContext.INSTANCE;
            if (serverContext.getTraceContext() != null) {
                traceId = RpcContext.getContext().getAttachment("traceId");
                parentSpanId = serverContext.getTraceContext().getParentSpanId();
                //当前spanId:
                curSpanId = serverContext.nextSpanId();

            } else {
                //非rpc上下文中第一次发起调用
                traceId = UUID.randomUUID().toString().replace("-", "");
                //在该调用结束之后,dubbo自动清楚,后面的链路都会有traceId
                RpcContext.getContext().setAttachment("traceId", traceId);
                curSpanId = "0";
                parentSpanId = "null";
            }
            String service = RpcContext.getContext().getUrl().getPath();
            String serviceApp = RpcContext.getContext().getUrl().getParameters().get("application");
            String serviceIp = RpcContext.getContext().getUrl().getParameters().get("register.ip");
            String methodName = RpcContext.getContext().getMethodName();
            RpcContext.getContext().setAttachment("parentSpanId", curSpanId);
            Date start = new Date();
            long startMs = System.currentTimeMillis();
            try {
                return callable.call();
            } finally {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Span span = Span.builder()
                        .rpcId(curSpanId)
                        .parentRpcId(parentSpanId)
                        .traceId(traceId)
                        .className(service)
                        .methodName(methodName)
                        .serviceApp(serviceApp)
                        .ip(serviceIp)
                        .startTime(format.format(start))
                        .endTime(format.format(new Date()))
                        .traceId(traceId)
                        .cost(System.currentTimeMillis() - startMs)
                        .build();
                span.finish();
                Collector.sendSpan(span);
            }
        } else {
            //服务端恢复trace链路
            TraceContext traceContext = new TraceContext();
            String serverTraceId = RpcContext.getContext().getAttachment("traceId");
            String serverParentId = RpcContext.getContext().getAttachment("parentSpanId");
            traceContext.setTraceId(serverTraceId);
            traceContext.setParentSpanId(serverParentId);
            //服务端上下文
            ServerContext.INSTANCE.setTraceContext(traceContext);
            try {
                return callable.call();
            } finally {
                //清理服务端上下文
                ServerContext.INSTANCE.clearTraceContext();
            }
        }
    }


}

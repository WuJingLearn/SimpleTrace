package org.javaboy.trace.server.storage;

import org.javaboy.trace.model.Span;
import org.javaboy.trace.model.Trace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 码劲
 */
public class TraceStorage {

    public static final TraceStorage INSTANCE = new TraceStorage();

    private static Map<String, List<Span>> traceCache = new ConcurrentHashMap();
    private static Map<String, String> cache = new HashMap<>();

    /**
     * 将链路信息按照traceId分组
     *
     * @param span
     */
    public void storeTrace(Span span) {
        List<Span> spanList = traceCache.computeIfAbsent(span.getTraceId(), (r) -> new CopyOnWriteArrayList<Span>());
        spanList.add(span);
    }

    /**
     * 根据traceId获取trace链路信息
     *
     * @param traceId
     * @return
     */
    public synchronized String getTrace(String traceId) {
        if (cache.get(traceId) != null) {
            return cache.get(traceId);
        }
        List<Span> spanList = traceCache.get(traceId);
        if (spanList == null) {
            return null;
        }
        System.out.println("spanList:"+spanList);
        //入口
        Span startSpan = spanList.stream().filter(span -> "0".equals(span.getRpcId())).findFirst().get();

        for (Span span : spanList) {
            String spanId = span.getRpcId();
            if ("0".equals(spanId)) {
                continue;
            }
            String parentSpanId = span.getParentRpcId();
            Span parentSpan = spanList.stream().filter(x -> parentSpanId.equals(x.getRpcId())).findFirst().get();
            parentSpan.addSonSpan(span);
        }
        //不同层级排序
        spanList.stream().forEach(Span::sortSonSpan);

        Trace trace = new Trace();
        trace.setTraceId(traceId);
        trace.setStartSpan(startSpan);
        cache.put(traceId, trace.toString());
        System.out.println("trace: " + trace.toString());
        return trace.toString();
    }


    public static void main(String[] args) {
//        Trace trace1 = new Trace();
//        TraceStorage traceStorage = new TraceStorage();
//        Span startSpan = new Span();
//        startSpan.setTraceId("abcd");
//        startSpan.setSpanId("0");
//        startSpan.setStartTime("0");
//        startSpan.setEndTime("10");
//
//        Span span1 = new Span();
//        span1.setTraceId("abcd");
//        span1.setSpanId("0.1");
//        span1.setParentSpanId("0");
//        span1.setStartTime("1");
//        span1.setEndTime("6");
//
//        Span span2 = new Span();
//        span2.setTraceId("abcd");
//        span2.setSpanId("0.2");
//        span2.setParentSpanId("0");
//        span2.setStartTime("6");
//        span2.setEndTime("10");
//
//        Span span3 = new Span();
//        span3.setTraceId("abcd");
//        span3.setSpanId("0.1.1");
//        span3.setParentSpanId("0.1");
//        span1.setStartTime("2");
//        span1.setEndTime("3");
//
//        Span span4 = new Span();
//        span4.setTraceId("abcd");
//        span4.setSpanId("0.1.2");
//        span4.setParentSpanId("0.1");
//        span1.setStartTime("4");
//        span1.setEndTime("5");
//
//
//        traceStorage.storeTrace(startSpan);
//        traceStorage.storeTrace(span1);
//        traceStorage.storeTrace(span2);
//        traceStorage.storeTrace(span3);
//        traceStorage.storeTrace(span4);
//
//        Trace trace = traceStorage.getTrace("abcd");
//
//        System.out.println(trace);


    }

}

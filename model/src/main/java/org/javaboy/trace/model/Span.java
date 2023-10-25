package org.javaboy.trace.model;

/**
 * @author 码劲
 */

import lombok.*;

import java.util.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Span {
    private String traceId;
    /**
     * 组织形式:
     * 0
     * 0.1 一级调用
     * 0.1.1 一级调用
     * 0.1.2
     */
    private String rpcId;
    private String parentRpcId;
    private String serviceApp;
    private String ip;
    private String className;
    private String methodName;
    private String startTime;
    private String endTime;
    /**
     * unit:ms
     */
    private long cost;
    private List<Span> sons;
    private Map<String, String> labels;
    private String errorInfo;


    public void addSonSpan(Span span) {
        if (sons == null) {
            sons = new ArrayList<>();
        }
        sons.add(span);
    }

    public static void sortSonSpan(Span span) {
        if (span.getSons() != null) {
            Collections.sort(span.getSons(), comparator);
        }
    }


    public void finish() {
        //发送至server
        System.out.println("发送Span到Server:" + this);
    }

    public String toString() {
        String str = "traceId: " + traceId + " rpcId: " + rpcId + " parentRpcId: " + parentRpcId + " cost: " + cost + " serverApp: " + serviceApp + " serverIp:" + ip + " className: " + className + " methodName: " + methodName
                + " startTime: " + startTime + " endTime: " + endTime + "<br/>";
        if (sons != null) {
            for (Span son : sons) {
                String sonStr = son.toString();
                str += sonStr;
            }
        }

        return str;
    }


    static Comparator<Span> comparator = (s1, s2) -> s1.rpcId.compareTo(s2.rpcId);
}

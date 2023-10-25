package org.javaboy.trace.model;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Trace {
    private String traceId;
    private Span startSpan;

    public String toString(){
        return startSpan.toString();
    }
}

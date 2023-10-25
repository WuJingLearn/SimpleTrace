package org.javaboy.trace.server.web;

import org.javaboy.trace.model.Span;
import org.javaboy.trace.server.storage.TraceStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 码劲
 */
@RestController
public class TraceController {

    private TraceStorage storage = TraceStorage.INSTANCE;

    @PostMapping("/receiveSpan")
    public String receiveSpan(@RequestBody Span span){
        System.out.println("接受到SPAN:"+span);
        storage.storeTrace(span);
        return "success";
    }

    /**
     * 查询链路信息
     * @param traceId
     * @return
     */
    @GetMapping("/trace")
    public String watchTrace(String traceId){
        String trace = storage.getTrace(traceId);
        return trace;
    }

}

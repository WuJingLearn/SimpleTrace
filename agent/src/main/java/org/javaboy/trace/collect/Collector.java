package org.javaboy.trace.collect;

import org.javaboy.trace.model.Span;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 码劲
 */
public class Collector {

    private static final String SERVER_ADDRESS = "http://127.0.0.1:8888/receiveSpan";

    static RestTemplate restTemplate = new RestTemplate();

    static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    public static void sendSpan(Span span) {
        System.out.println("异步发送span");
        executor.execute(() -> {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Span> entity = new HttpEntity<>(span, header);
            restTemplate.postForObject(SERVER_ADDRESS, entity, String.class);
        });
    }

}

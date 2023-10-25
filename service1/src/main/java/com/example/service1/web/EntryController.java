package com.example.service1.web;

import com.example.service.Service2;
import com.example.service.Service3;
import com.example.service1.dubbo.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 码劲
 */
@RestController
public class EntryController {



    @Reference
    HelloService client;

    @Reference(timeout = 10000)
    Service2 service2;

    @Reference(timeout = 10000)
    Service3 service3;

    @GetMapping("/rpctest")
    public String rpc() {
        String traceId = client.hello();
        System.out.println("traceId: "+traceId);
        return traceId;
    }

    /**
     * ServerContext:
     *
     * @return
     */

    @GetMapping("/context")
    public String context() {
        //span 0
        service2.service2();
        //span 0 0.1
        service3.service3();
        return "helloWorld";
    }


}

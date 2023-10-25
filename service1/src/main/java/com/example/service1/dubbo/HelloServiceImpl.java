package com.example.service1.dubbo;

import com.example.service.Service2;
import com.example.service.Service3;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author 码劲
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Reference(timeout = 10000,check = false)
    Service2 service2;

    @Reference(timeout = 10000,check = false)
    Service3 service3;
    //-javaagent:/Users/jingmac/IdeaProjects/simpleTrace/agent/target/agent-1.0-SNAPSHOT.jar
    @Override
    public String hello() {
        service2.service2();
        service3.service3();
        return RpcContext.getContext().getAttachment("traceId");
    }

    @Override
    public String world() {
        return null;
    }
}

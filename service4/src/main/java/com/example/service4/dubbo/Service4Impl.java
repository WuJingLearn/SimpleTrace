package com.example.service4.dubbo;

import com.example.service.Service4;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author 码劲
 */
@Service
public class Service4Impl implements Service4 {
    @Override
    public String service4() {
        String traceId = RpcContext.getContext().getAttachment("traceId");
        System.out.println("service4-->" + traceId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hh";
    }
}

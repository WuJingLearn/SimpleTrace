package com.example.service2.dubbo;

import com.example.service.Service2;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author 码劲
 */
@Service
public class Service2Impl implements Service2 {
    @Override
    public String service2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "111";
    }
}

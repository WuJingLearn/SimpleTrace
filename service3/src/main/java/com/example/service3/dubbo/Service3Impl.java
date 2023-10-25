package com.example.service3.dubbo;

import com.example.service.Service3;
import com.example.service.Service4;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author 码劲
 */

@Service
public class Service3Impl implements Service3 {

    @Reference(timeout = 10000, check = false)
    Service4 service4;

    @Override
    public String service3() {
        try {
            Thread.sleep(500);
            service4.service4();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "111";
    }
}

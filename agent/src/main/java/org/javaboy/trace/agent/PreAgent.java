package org.javaboy.trace.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.javaboy.trace.enhence.DubboInterceptor;

import java.lang.instrument.Instrumentation;

/**
 * @author 码劲
 */
public class PreAgent {
    public static void premain(String args, Instrumentation instrumentation) {

        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                return builder.method(ElementMatchers.named("invoke")).intercept(MethodDelegation.to(DubboInterceptor.class));
            }
        };
        /**
         * 增强不同的的类,使用不同的Type进行增强。
         */
        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("org.apache.dubbo.monitor.support.MonitorFilter"))
                .transform(transformer)
                .installOn(instrumentation);

    }

}

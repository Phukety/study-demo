package com.phukety.demo.gc;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * 元空间内存溢出demo
 * 设置元空间最大内存30M: -XX:MaxMetaspaceSize=10M -XX:MetaspaceSize=10M
 */
public class MetaSpaceOverFlowDemo {
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Hello.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> {
                System.out.println("Enhanced hello");
                // 调用Hello.say()
                return proxy.invokeSuper(obj, args1);
            });
            Hello enhancedOOMObject = (Hello) enhancer.create();
            enhancedOOMObject.say();
            System.out.println(enhancedOOMObject.getClass().getName());
        }
    }
}

class Hello {
    public void say() {
        System.out.println("Hello Student");
    }
}

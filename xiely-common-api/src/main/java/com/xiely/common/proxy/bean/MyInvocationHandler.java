package com.xiely.common.proxy.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler
{
    private void aopMethod()
    {
        System.out.println("before method");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        aopMethod();
        return method.invoke(proxy, args);
    }
}

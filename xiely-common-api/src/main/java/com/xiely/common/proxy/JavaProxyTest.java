package com.xiely.common.proxy;

import java.lang.reflect.Proxy;

import com.xiely.common.proxy.bean.ConcreteClass;
import com.xiely.common.proxy.bean.JavaProxyInterface;
import com.xiely.common.proxy.bean.MyInvocationHandler;

@SuppressWarnings("restriction")
public class JavaProxyTest
{
    public static void main(String[] args)
    {
        JavaProxyInterface javaProxyInterface = new ConcreteClass();

        JavaProxyInterface newJavaProxyInterface = (JavaProxyInterface) Proxy.newProxyInstance(JavaProxyTest.class.getClassLoader(), new Class[]{JavaProxyInterface.class}, new MyInvocationHandler());

        newJavaProxyInterface.gotoSchool();
        newJavaProxyInterface.gotoWork();
        newJavaProxyInterface.oneDay();
        newJavaProxyInterface.oneDayFinal();
    }
}

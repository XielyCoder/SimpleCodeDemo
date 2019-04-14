package com.xiely.common.proxy.bean;

public class ConcreteClass implements JavaProxyInterface
{
    @Override
    public void gotoSchool()
    {
        System.out.println("goto school.");
    }

    @Override
    public void gotoWork()
    {
        System.out.println("goto work.");
    }

    @Override
    public void oneDay()
    {
        gotoSchool();
        gotoWork();
    }

    @Override
    public void oneDayFinal()
    {
        gotoSchool();
        gotoWork();
    }
}

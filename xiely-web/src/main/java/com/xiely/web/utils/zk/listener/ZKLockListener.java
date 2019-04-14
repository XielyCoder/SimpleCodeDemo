package com.xiely.web.utils.zk.listener;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZKLockListener implements IZkDataListener
{
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void handleDataChange(String s, Object o)
    {
    }

    @Override
    public void handleDataDeleted(String s)
    {
        //锁已经释放，唤醒阻塞线程。
        countDownLatch.countDown();
    }

    void waitCountDownLatch()
    {
        try
        {
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

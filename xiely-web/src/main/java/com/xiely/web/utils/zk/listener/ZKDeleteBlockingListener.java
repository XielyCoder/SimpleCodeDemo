package com.xiely.web.utils.zk.listener;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

public class ZKDeleteBlockingListener implements IZkDataListener
{
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private ZkClient zkClient;

    private String localPath;

    public ZKDeleteBlockingListener(ZkClient zkClient, String lockPath)
    {
        this.zkClient = zkClient;
        this.localPath = lockPath;
    }

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

    public void awaitLock()
    {
        zkClient.subscribeDataChanges(localPath, this);
        dowait();
        zkClient.unsubscribeDataChanges(localPath, this);
    }

    public void dowait()
    {
        if (this.zkClient.exists(localPath))
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
}

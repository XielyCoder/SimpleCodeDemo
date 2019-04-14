package com.xiely.web.utils.zk.listener;

import org.I0Itec.zkclient.IZkChildListener;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.xiely.web.utils.zk.ClientProxy;

public class ZKCyclicBarrierListener implements IZkChildListener
{
    private CountDownLatch countDownLatch = new CountDownLatch(1);


    @Override
    public void handleChildChange(String parentPath, List<String> currentChildren)
    {
        try
        {
            String waitNum = ClientProxy.getZkClient().readData(parentPath);
            if (StringUtils.equals(String.valueOf(currentChildren.size()), waitNum))
            {
                countDownLatch.countDown();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

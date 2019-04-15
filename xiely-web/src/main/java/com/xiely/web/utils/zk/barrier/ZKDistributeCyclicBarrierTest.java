package com.xiely.web.utils.zk.barrier;

import com.xiely.web.utils.zk.ClientProxy;

public class ZKDistributeCyclicBarrierTest
{

    public static void main(String[] args)
    {
        delete();
        ZKDistributeCyclicBarrier barrier = new ZKDistributeCyclicBarrier(10);
        for (int i = 0; i < 100; i++)
        {
            startWork(String.valueOf(i), barrier);
        }
        delete();
    }

    private static void startWork(String num, ZKDistributeCyclicBarrier barrier)
    {
        new Thread(() -> barrier.await(num)).start();
    }

    private static void delete()
    {
        String defaultBarrierRootNode = String.format("/barrierCondition_%s", 10);
        ClientProxy.getZkClient().deleteRecursive(defaultBarrierRootNode);
    }
}

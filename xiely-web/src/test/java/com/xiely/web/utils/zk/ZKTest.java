package com.xiely.web.utils.zk;

import org.junit.Test;

import com.xiely.web.utils.zk.barrier.ZKDistributeCyclicBarrier;

public class ZKTest
{

    private String barrierCondition = "/barrierCondition";

    private String barrier = barrierCondition + "/barrier";
    @Test
    public void delete()
    {
        ClientProxy.getZkClient().deleteRecursive(barrier);
    }

    public static void main(String[] args)
    {
        ZKDistributeCyclicBarrier barrier = new ZKDistributeCyclicBarrier(10);
        for (int i = 0; i < 1000; i++)
        {
            startWork(String.valueOf(i), barrier);
        }
    }

    private static void startWork(String num, ZKDistributeCyclicBarrier barrier)
    {
        new Thread(() -> barrier.await(num)).start();
    }
}

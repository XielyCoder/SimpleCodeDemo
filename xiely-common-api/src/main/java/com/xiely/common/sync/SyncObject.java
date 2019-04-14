package com.xiely.common.sync;

import java.util.concurrent.ConcurrentHashMap;

public class SyncObject
{
    private static int count;

    public synchronized void methodSync()
    {
        count++;
        System.out.println("method sync: " + count);
    }

    public void codeSync()
    {
        //this 锁当前对象的code，object.class锁所有对象的code。
        synchronized (SyncObject.class)
        {
            count++;
            System.out.println("code sync: " + count);
            try
            {
                Thread.sleep(10000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public synchronized static void staticSync()
    {
        count++;
        System.out.println("static sync: " + count);
    }
}

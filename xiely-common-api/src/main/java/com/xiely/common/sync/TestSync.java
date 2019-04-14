package com.xiely.common.sync;

public class TestSync
{
    public static void main(String[] args)
    {
        codeSyncThread();
    }

    private static void staticSyncThread()
    {
        for (int i = 0; i < 10; i++)
        {
            new Thread(SyncObject::staticSync).start();
        }
    }

    private static void codeSyncThread()
    {
        SyncObject syncObject = new SyncObject();
        for (int i = 0; i < 10; i++)
        {
            new Thread(syncObject::codeSync).start();
            syncObject.methodSync();
        }
    }

    private static void methodSyncThread()
    {
        SyncObject syncObject = new SyncObject();
        for (int i = 0; i < 10; i++)
        {
            new Thread(syncObject::methodSync).start();
        }
    }
}

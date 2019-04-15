package com.xiely.web.utils.zk.lock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import com.xiely.web.utils.zk.ClientProxy;
import com.xiely.web.utils.zk.listener.ZKDeleteBlockingListener;

public class ZKDistributeLock implements Lock
{

    private String lockPath;

    private ZkClient zkClient;

    public ZKDistributeLock(String lockPath)
    {
        if (lockPath == null || lockPath.trim().equals(""))
        {
            throw new IllegalArgumentException("patch不能为空字符串");
        }
        this.lockPath = lockPath;

        zkClient = ClientProxy.getZkClient();
    }

    @Override
    public boolean tryLock()
    { // 不会阻塞
        // 创建节点
        try
        {
            zkClient.createEphemeral(lockPath);
        }
        catch (ZkNodeExistsException e)
        {
            return false;
        }
        return true;
    }

    @Override
    public void unlock()
    {
        zkClient.delete(lockPath);
    }

    @Override
    public void lock()
    {
        // 如果获取不到锁，阻塞等待
        if (!tryLock())
        {
            // 没获得锁，阻塞自己
            new ZKDeleteBlockingListener(zkClient, lockPath).awaitLock();
            // 再次尝试
            lock();
        }
    }

    @Override
    public void lockInterruptibly()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Condition newCondition()
    {
        // TODO Auto-generated method stub
        //noinspection ConstantConditions
        return null;
    }

}

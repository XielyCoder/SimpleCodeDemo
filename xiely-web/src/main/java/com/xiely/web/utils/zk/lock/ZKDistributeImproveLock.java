package com.xiely.web.utils.zk.lock;

import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import com.xiely.web.utils.zk.ClientProxy;
import com.xiely.web.utils.zk.listener.ZKDeleteBlockingListener;

public class ZKDistributeImproveLock implements Lock
{
    /*
     * 利用临时顺序节点来实现分布式锁
     * 获取锁：取排队号（创建自己的临时顺序节点），然后判断自己是否是最小号，如是，则获得锁；不是，则注册前一节点的watcher,阻塞等待
     * 释放锁：删除自己创建的临时顺序节点
     */
    private String lockPath;

    private ZkClient zkClient;

    private ThreadLocal<String> currentPath = new ThreadLocal<>();

    private ThreadLocal<String> beforePath = new ThreadLocal<>();

    // 锁重入计数器
    private ThreadLocal<Integer> reenterCount = ThreadLocal.withInitial(() -> 0);

    public ZKDistributeImproveLock(String lockPath)
    {
        if (lockPath == null || lockPath.trim().equals(""))
        {
            throw new IllegalArgumentException("patch不能为空字符串");
        }
        this.lockPath = lockPath;
        zkClient = ClientProxy.getZkClient();
        ClientProxy.createPersistentIfNotExist(lockPath);
    }

    @Override
    public boolean tryLock()
    {
        if (currentPath.get() == null || !zkClient.exists(currentPath.get()))
        {
            String node = zkClient.createEphemeralSequential(lockPath + "/", "locked");
            currentPath.set(node);
            reenterCount.set(0);
        }
        // 获得所有的子节点。
        List<String> children = zkClient.getChildren(lockPath);
        // 排序list
        Collections.sort(children);

        // 判断当前节点是否是最小的
        if (currentPath.get().equals(lockPath + "/" + children.get(0)))
        {
            // 锁重入计数
            reenterCount.set(reenterCount.get() + 1);
            return true;
        }
        else
        {
            // 取到前一个
            // 得到字节的索引号
            int curIndex = children.indexOf(currentPath.get().substring(lockPath.length() + 1));
            String node = lockPath + "/" + children.get(curIndex - 1);
            beforePath.set(node);
        }
        return false;
    }

    @Override
    public void lock()
    {
        if (!tryLock())
        {
            // 阻塞等待
            new ZKDeleteBlockingListener(zkClient, beforePath.get()).awaitLock();
            // 再次尝试加锁
            lock();
        }
    }

    @Override
    public void unlock()
    {
        if (reenterCount.get() > 1)
        {
            // 重入次数减1，释放锁
            reenterCount.set(reenterCount.get() - 1);
            return;
        }
        // 删除节点
        if (currentPath.get() != null)
        {
            zkClient.delete(currentPath.get());
            currentPath.set(null);
            reenterCount.set(0);
        }
    }

    @Override
    public void lockInterruptibly()
    {
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit)
    {
        return false;
    }

    @Override
    public Condition newCondition()
    {
        //noinspection ConstantConditions
        return null;
    }
}

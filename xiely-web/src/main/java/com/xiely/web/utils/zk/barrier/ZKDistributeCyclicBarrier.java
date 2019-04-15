package com.xiely.web.utils.zk.barrier;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;

import com.xiely.web.utils.zk.ClientProxy;
import com.xiely.web.utils.zk.listener.ZKDeleteBlockingListener;
import com.xiely.web.utils.zk.lock.ZKDistributeImproveLock;

public class ZKDistributeCyclicBarrier
{
    private ZkClient zkClient;

    private String barrier;

    private String condition;

    private ZKDistributeImproveLock zkDistributeLock;

    public ZKDistributeCyclicBarrier(int parties)
    {
        String defaultBarrierRootNode = String.format("/barrierCondition_%s", parties);
        zkClient = ClientProxy.getZkClient();
        ClientProxy.createPersistentIfNotExist(defaultBarrierRootNode, String.valueOf(parties));
        barrier = defaultBarrierRootNode + "/barrier";
        condition = zkClient.readData(defaultBarrierRootNode);
        zkDistributeLock = new ZKDistributeImproveLock(defaultBarrierRootNode + "/lock");
    }


    public void await(String num)
    {
        boolean lastOne = false;
        try
        {
            System.out.println(num + "准备打开会议室大门。");
            //当前条件未进入队列阻塞。
            while (true)
            {
                zkDistributeLock.lock();
                if (!satisfyConditions())
                {
                    //当前条件进入队列
                    zkClient.createPersistentSequential(barrier + "/", "condition");
                    if (satisfyConditions())
                    {
                        System.out.println("====================人数到齐，会议开始。");
                        //满足条件释放栅栏。
                        zkClient.deleteRecursive(barrier);
                        //释放栅栏的，不需要阻塞。
                        lastOne = true;
                    }
                    //阻塞处理。
                    dowait(lastOne);
                    //条件满足开始执行。
                    System.out.println(num + "发言。");
                    break;
                }
                //满足条件释放栅栏，非人为情况不会走到该分支。
                zkClient.deleteRecursive(barrier);
                zkDistributeLock.unlock();
            }
        }
        finally
        {
            zkDistributeLock.unlock();
        }
    }

    private void dowait(boolean lastOne)
    {
        if (!lastOne)
        {
            ZKDeleteBlockingListener listener = new ZKDeleteBlockingListener(zkClient, barrier);
            zkClient.subscribeDataChanges(barrier, listener);
            //监听开始后&阻塞前释放锁，以保证监听开始前barrier不会被删除，不会因为阻塞导致锁不释放。
            zkDistributeLock.unlock();
            listener.dowait();
            zkClient.unsubscribeDataChanges(barrier, listener);
        }
        else
        {
            zkDistributeLock.unlock();
        }
    }

    private boolean satisfyConditions()
    {
        ClientProxy.createPersistentIfNotExist(barrier, "");
        return StringUtils.equals(String.valueOf(zkClient.countChildren(barrier)), condition);
    }
}

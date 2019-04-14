package com.xiely.web.utils.zk.barrier;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;

import com.xiely.web.utils.zk.ClientProxy;
import com.xiely.web.utils.zk.lock.ZKDistributeImproveLock;

public class ZKDistributeCyclicBarrier
{
    private ZkClient zkClient;

    private String defaultBarrierRootNode;

    private String barrier;

    private ZKDistributeImproveLock zkDistributeLock;

    public ZKDistributeCyclicBarrier(int parties)
    {
        defaultBarrierRootNode = String.format("/barrierCondition_%s", parties);
        barrier = defaultBarrierRootNode + "/barrier";
        zkClient = ClientProxy.getZkClient();
        ClientProxy.createPersistentIfNotExist(defaultBarrierRootNode, String.valueOf(parties));
        zkDistributeLock = new ZKDistributeImproveLock(defaultBarrierRootNode + "/lock");
    }

    public void await(String num)
    {
        try
        {
            System.out.println(num + "准备打开会议室大门。");
            //当前条件未进入队列阻塞。
            while (true)
            {
                //获取分布式锁，修改条件。
                zkDistributeLock.lock();
                if (!satisfyConditions())
                {
                    //当前条件进入队列
                    zkClient.createPersistentSequential(barrier + "/", "condition");
                    if (satisfyConditions())
                    {
                        System.out.println("====================人数到齐，会议开始。");
                        //满足条件删除栅栏。
                        zkClient.deleteRecursive(barrier);
                    }
                    //修改成功后释放锁。
                    zkDistributeLock.unlock();
                    //栅栏存在阻塞。
                    //noinspection StatementWithEmptyBody
                    while (existBarrier())
                        ;
                    //条件满足开始执行。
                    System.out.println(num + "发言。");
                    break;
                }
                else
                {
                    System.out.println("*****************************发现当前会议室被占用。");
                    zkDistributeLock.unlock();
                }
            }
        }
        finally
        {
            zkDistributeLock.unlock();
        }
    }

    private boolean satisfyConditions()
    {
        ClientProxy.createPersistentIfNotExist(barrier, "");
        String condition = zkClient.readData(defaultBarrierRootNode);
        return StringUtils.equals(String.valueOf(zkClient.countChildren(barrier)), condition);
    }

    private boolean existBarrier()
    {
        return zkClient.exists(barrier);
    }
}

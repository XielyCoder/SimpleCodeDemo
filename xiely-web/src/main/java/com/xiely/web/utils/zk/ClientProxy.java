package com.xiely.web.utils.zk;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.commons.lang3.StringUtils;

public class ClientProxy
{
    private static ZkClient zkClient;

    static
    {
        zkClient = new ZkClient("192.168.0.11:2181,192.168.0.12:2181,192.168.0.13:2181");
        zkClient.setZkSerializer(new ZKSerializerUtf8());
    }

    public static ZkClient getZkClient()
    {
        return zkClient;
    }

    public static void createPersistentIfNotExist(String zNode)
    {
        createPersistentIfNotExist(zNode, null);
    }

    public static void createPersistentIfNotExist(String zNode, String value)
    {
        if (!zkClient.exists(zNode))
        {
            try
            {
                zkClient.createPersistent(zNode, true);
                if (StringUtils.isNotEmpty(value))
                {
                    zkClient.writeData(zNode, value);
                }
            }
            catch (ZkNodeExistsException ignored)
            {
            }
        }
    }
}

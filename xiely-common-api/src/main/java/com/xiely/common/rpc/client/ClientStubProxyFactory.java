package com.xiely.common.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.xiely.common.rpc.client.net.NetClient;
import com.xiely.common.rpc.common.protocol.MessageProtocol;
import com.xiely.common.rpc.common.protocol.Request;
import com.xiely.common.rpc.common.protocol.Response;
import com.xiely.common.rpc.discovery.ServiceInfo;
import com.xiely.common.rpc.discovery.ServiceInfoDiscoverer;

/**
 * ClientStubProxyFactory
 * 客户端存根代理工厂
 */
public class ClientStubProxyFactory
{

    private ServiceInfoDiscoverer sid;

    private Map<String, MessageProtocol> supportMessageProtocols;

    private NetClient netClient;

    private Map<Class<?>, Object> objectCache = new HashMap<>();

    /**
     * @param <T>
     * @param interf
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> interf)
    {
        T obj = (T) this.objectCache.get(interf);
        if (obj == null)
        {
            obj = (T) Proxy.newProxyInstance(interf.getClassLoader(), new Class<?>[]{interf}, new ClientStubInvocationHandler(interf));
            this.objectCache.put(interf, obj);
        }

        return obj;
    }

    public ServiceInfoDiscoverer getSid()
    {
        return sid;
    }

    public void setSid(ServiceInfoDiscoverer sid)
    {
        this.sid = sid;
    }

    public Map<String, MessageProtocol> getSupportMessageProtocols()
    {
        return supportMessageProtocols;
    }

    public void setSupportMessageProtocols(Map<String, MessageProtocol> supportMessageProtocols)
    {
        this.supportMessageProtocols = supportMessageProtocols;
    }

    public NetClient getNetClient()
    {
        return netClient;
    }

    public void setNetClient(NetClient netClient)
    {
        this.netClient = netClient;
    }

    /**
     * ClientStubInvocationHandler
     * 客户端存根代理调用实现
     */
    private class ClientStubInvocationHandler implements InvocationHandler
    {
        private Class<?> interf;

        public ClientStubInvocationHandler(Class<?> interf)
        {
            super();
            this.interf = interf;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {

            // 1、获得服务信息
            String serviceName = this.interf.getName();
            ServiceInfo sinfo = sid.getServiceInfo(serviceName);

            if (sinfo == null)
            {
                throw new Exception("远程服务不存在！");
            }

            // 2、构造request对象
            Request req = new Request();
            req.setServiceName(sinfo.getName());
            req.setMethod(method.getName());
            req.setPrameterTypes(method.getParameterTypes());
            req.setParameters(args);

            // 3、协议层编组
            // 获得该方法对应的协议
            MessageProtocol protocol = supportMessageProtocols.get(sinfo.getProtocol());
            // 编组请求
            byte[] data = protocol.marshallingRequest(req);

            // 4、调用网络层发送请求
            byte[] repData = netClient.sendRequest(data, sinfo);

            // 5解组响应消息
            Response rsp = protocol.unmarshallingResponse(repData);

            // 6、结果处理
            if (rsp.getException() != null)
            {
                throw rsp.getException();
            }

            return rsp.getReturnValue();
        }
    }
}

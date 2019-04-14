package com.xiely.web.utils;

import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean
{
    @Setter
    private Resource addressConfig;

    @Setter
    private String addressKeyPrefix;

    private JedisCluster jedisCluster;

    @Setter
    private Integer timeout;

    @Setter
    private Integer maxAttempts;

    @Setter
    private GenericObjectPoolConfig genericObjectPoolConfig;

    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

    @Override
    public JedisCluster getObject()
    {
        return jedisCluster;
    }

    @Override
    public Class<? extends JedisCluster> getObjectType()
    {
        return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        jedisCluster = new JedisCluster(parseHostAndPort(), timeout, maxAttempts, genericObjectPoolConfig);
    }

    private Set<HostAndPort> parseHostAndPort() throws Exception
    {
        try
        {
            Properties prop = new Properties();
            prop.load(this.addressConfig.getInputStream());
            Set<HostAndPort> hostAndPorts = new HashSet<>();
            for (Object key : prop.keySet())
            {
                if (!((String) key).startsWith(addressKeyPrefix))
                {
                    continue;
                }
                String val = (String) prop.get(key);
                boolean isIpPort = p.matcher(val).matches();
                if (!isIpPort)
                {
                    throw new IllegalArgumentException("ip 或 port 不合法");
                }
                String[] ipAndPort = val.split(":");
                HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                hostAndPorts.add(hap);
            }
            return hostAndPorts;
        }
        catch (IllegalArgumentException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new Exception("解析 jedis 配置文件失败" + addressKeyPrefix, ex);
        }
    }
}

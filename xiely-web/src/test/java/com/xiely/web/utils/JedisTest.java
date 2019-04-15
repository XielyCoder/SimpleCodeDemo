package com.xiely.web.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:resources/applicationContext.xml"})
public class JedisTest
{
    @Test
    public void test()
    {
        JedisCluster jedisCluster = SpringContextHolder.getBean("jedisCluster");
        String map1 = "map1";
        jedisCluster.hset(map1, "xiely", "111");
        System.out.println(jedisCluster.hgetAll(map1));
        jedisCluster.hdel(map1, "xiely");
        System.out.println(jedisCluster.hgetAll(map1));
    }
}

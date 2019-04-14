package com.xiely.web.sql.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class ExamplePlugin implements Interceptor
{
    /**
     * <!-- mybatis-config.xml -->
     * <plugins>
     *   <plugin interceptor="org.mybatis.example.ExamplePlugin">
     *     <property name="someProperty" value="100"/>
     *   </plugin>
     * </plugins>
     */

    public Object intercept(Invocation invocation) throws Throwable
    {
        return invocation.proceed();
    }

    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties)
    {
    }
}

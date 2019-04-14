package com.xiely.web.sql.objectFactory;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class ExampleObjectFactory extends DefaultObjectFactory
{
    /**
     * <!-- mybatis-config.xml -->
     * <objectFactory type="org.mybatis.example.ExampleObjectFactory">
     *   <property name="someProperty" value="100"/>
     * </objectFactory>
     */

    public <T> T create(Class<T> type)
    {
        return super.create(type);
    }

    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs)
    {
        return super.create(type, constructorArgTypes, constructorArgs);
    }

    public void setProperties(Properties properties)
    {
        super.setProperties(properties);
    }

    public <T> boolean isCollection(Class<T> type)
    {
        return Collection.class.isAssignableFrom(type);
    }
}

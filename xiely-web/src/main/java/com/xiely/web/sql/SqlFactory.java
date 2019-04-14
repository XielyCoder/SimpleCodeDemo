package com.xiely.web.sql;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

import java.io.IOException;
import java.io.InputStream;

public class SqlFactory
{
    private static SqlSessionFactory getIbatiesSeesionFactory()
    {
        String resource = "org/mybatis/example/mybatis-config.xml";
        SqlSessionFactory build = null;
        try
        {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            build = new SqlSessionFactoryBuilder().build(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return build;
    }

    private static void test()
    {
        DataSource dataSource = null;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(Object.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    private static void testSession()
    {
        SqlSession session = getIbatiesSeesionFactory().openSession();
        try
        {
            Object blog = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
        }
        finally
        {
            session.close();
        }
    }

    private static void testSession2()
    {
        SqlSession session = getIbatiesSeesionFactory().openSession();
        try
        {
            Object mapper = session.getMapper(Object.class);
        }
        finally
        {
            session.close();
        }
    }
}

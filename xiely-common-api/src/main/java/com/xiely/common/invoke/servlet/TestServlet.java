package com.xiely.common.invoke.servlet;

import javax.servlet.http.HttpServlet;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiely.common.invoke.annotation.XielyAutowire;
import com.xiely.common.invoke.annotation.XielyContoller;
import com.xiely.common.invoke.annotation.XielyMapping;
import com.xiely.common.invoke.annotation.XielyServer;

public class TestServlet extends HttpServlet
{
    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> xielyIoc = new HashMap<>();

    private void doScanPackage(String packages)
    {
        URL url = this.getClass().getClassLoader().getResource("/" + packages.replace("\\.", "/"));
        String fileStr = url.getFile();
        File files = new File(fileStr);
        for (File file : files.listFiles())
        {
            if (file.isDirectory())
            {
                doScanPackage(packages + "." + file.getName());
            }
            else
            {
                classNames.add(packages + "." + file.getName());
            }
        }
    }

    private void doInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        for (String className : classNames)
        {
            String cn = className.replace(".class", "");
            Class<?> clazz = Class.forName(cn);
            if (clazz.isAnnotationPresent(XielyContoller.class))
            {
                Object controllerInstance = clazz.newInstance();
                XielyMapping annotation = clazz.getAnnotation(XielyMapping.class);
                String key = annotation.value();
                xielyIoc.put(key, controllerInstance);
            }
            else if (clazz.isAnnotationPresent(XielyServer.class))
            {
                Object serverInstance = clazz.newInstance();
                XielyMapping annotation = clazz.getAnnotation(XielyMapping.class);
                String key = annotation.value();
                xielyIoc.put(key, serverInstance);
            }
        }
    }

    private void doAutowire()
    {
        for (Map.Entry<String, Object> entry : xielyIoc.entrySet())
        {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(XielyContoller.class))
            {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields)
                {
                    if (field.isAnnotationPresent(XielyAutowire.class))
                    {
                        XielyAutowire annotation = field.getAnnotation(XielyAutowire.class);
                        String key = annotation.value();
                        field.setAccessible(true);
                    }
                }
            }
        }
    }
}

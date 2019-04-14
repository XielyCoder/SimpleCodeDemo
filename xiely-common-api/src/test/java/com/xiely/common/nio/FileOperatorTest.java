package com.xiely.common.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.Arrays;

public class FileOperatorTest
{
    @Test
    public void test()
    {
        ByteBuffer buffer = ByteBuffer.allocate(88);
        System.out.println(buffer);

        String value = "Netty权威指南";
        System.out.println(value.getBytes().length);
        buffer.put(value.getBytes());
        System.out.println(buffer);

        buffer.flip();
        System.out.println(buffer);

        byte[] v = new byte[buffer.remaining()];
        buffer.get(v);

        System.out.println(buffer);
        System.out.println(new String(v));
    }

    @Test
    public void test2()
    {
        String srcPath = "C:\\Users\\59403\\Desktop\\jdk api 1.8_google\\jb51.net.txt";
        String tagPath = "C:\\Users\\59403\\Desktop\\jdk api 1.8_google\\test.txt";
        String charset = "UTF-8";
        String msg = FileOperator.readMsgFromFile(srcPath, charset,1024);
        System.out.println(msg);
        FileOperator.writeMsgToFile(msg, tagPath, charset,2048);
    }
    @Test
    public void testtt()
    {
        System.out.println(SelectionKey.OP_WRITE!=0);
    }
}

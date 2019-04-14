package com.xiely.common.nio.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import com.xiely.common.nio.bean.Buffers;

public class SelectionKeyUtils
{
    public static void writeChannel(SelectionKey key, String msg) throws IOException
    {
        SocketChannel sc = (SocketChannel) key.channel();
        Buffers buffers = (Buffers) key.attachment();
        ByteBuffer writeBuf = buffers.gerWriteBuffer();
        writeBuf.put(msg.getBytes(StandardCharsets.UTF_8));
        writeBuf.flip();
        int len = 0;
        while (writeBuf.hasRemaining())
        {
            len = sc.write(writeBuf);
            /*说明底层的socket写缓冲已满*/
            if (len == 0)
            {
                System.out.println("full socket");
                break;
            }
        }
        writeBuf.compact();
        /*说明数据全部写入到底层的socket写缓冲区*/
        if (len != 0)
        {
            System.out.println("write finish");
            /*取消通道的写事件*/
            key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
        }
    }

    public static void readChannel(SelectionKey key)
    {
        /*通过SelectionKey获取对应的通道*/
        try
        {
            SocketChannel sc = (SocketChannel) key.channel();
            /*通过SelectionKey获取通道对应的缓冲区*/
            Buffers buffers = (Buffers) key.attachment();
            ByteBuffer byteBuf = buffers.getReadBuffer();
            /*从底层socket读缓冲区中读入数据*/
            sc.read(byteBuf);
            byteBuf.flip();
            /*解码显示，客户端发送来的信息*/
            CharBuffer charBuf = BufferUtils.decode(byteBuf);
            System.out.println(charBuf.array());
            byteBuf.clear();
            charBuf.clear();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

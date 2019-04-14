package com.xiely.common.nio;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.xiely.common.nio.bean.Buffers;
import com.xiely.common.nio.utils.SelectionKeyUtils;

/*服务器端，:接收客户端发送过来的数据并显示，
 *服务器把上接收到的数据加上"echo from service:"再发送回去*/
public class TCPNonBlockSerSocket
{
    public static void main(String[] args)
    {
        Thread thread = new Thread(new TCPEchoServer(8080));
        thread.start();
    }

    public static class TCPEchoServer implements Runnable
    {
        /*服务器地址*/
        private InetSocketAddress localAddress;

        /*服务通道*/
        private ServerSocketChannel ssc;

        /*选择器*/
        private Selector selector;

        TCPEchoServer(int port)
        {
            this.localAddress = new InetSocketAddress(port);
        }

        @Override
        public void run()
        {
            System.out.println("Server start with address : " + localAddress);
            /*服务器线程被中断后会退出*/
            try
            {
                initChannel();
                //noinspection InfiniteLoopStatement
                while (true)
                {
                    selector.select();
                    opSelectedKeys(selector.selectedKeys().iterator());
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException | IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                //noinspection deprecation
                IOUtils.closeQuietly(selector);
                //noinspection deprecation
                IOUtils.closeQuietly(ssc);
            }
        }

        private void initChannel() throws IOException
        {
            /*创建服务器通道*/
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            /*设置监听服务器的端口，设置最大连接缓冲数为100*/
            ssc.bind(localAddress, 100);
            /*创建选择器*/
            selector = Selector.open();
            /*服务器通道只能对tcp链接事件感兴趣*/
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        }

        private void opSelectedKeys(Iterator<SelectionKey> iterator) throws IOException
        {
            while (iterator.hasNext())
            {
                SelectionKey key = iterator.next();
                /*防止下次select方法返回已处理过的通道*/
                iterator.remove();
                System.out.println("S exc read " + key.isReadable() + " write " + key.isWritable());

                /*ssc通道只能对链接事件感兴趣*/
                if (key.isAcceptable())
                {
                    registerAccept();
                }

                /*（普通）通道感兴趣读事件且有数据可读*/
                if (key.isReadable())
                {
                    SelectionKeyUtils.readChannel(key);
                }

                /*通道感兴趣写事件且底层缓冲区有空闲*/
                if (key.isWritable())
                {
                    SelectionKeyUtils.writeChannel(key, "消息已收到！");
                }
            }
        }

        private void registerAccept() throws IOException
        {
            /*accept方法会返回一个普通通道，每个通道在内核中都对应一个socket缓冲区*/
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            /*向选择器注册这个通道和普通通道感兴趣的事件，同时提供这个新通道相关的缓冲区*/
            sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, new Buffers(256, 256));
            System.out.println("Accept from " + sc.getRemoteAddress());
        }
    }
}
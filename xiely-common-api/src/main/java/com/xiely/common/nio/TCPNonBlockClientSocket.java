package com.xiely.common.nio;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.xiely.common.nio.bean.Buffers;
import com.xiely.common.nio.utils.SelectionKeyUtils;

/*客户端:客户端每隔1~2秒自动向服务器发送数据，接收服务器接收到数据并显示*/
public class TCPNonBlockClientSocket implements Runnable
{
    /*服务器的ip地址+端口port*/
    private InetSocketAddress remoteAddress;

    /*选择器*/
    private Selector selector;

    public TCPNonBlockClientSocket(int port)
    {
        remoteAddress = new InetSocketAddress(port);
    }

    @Override
    public void run()
    {
        try
        {
            initSelector();
            //noinspection InfiniteLoopStatement
            while (true)
            {
                getSelection();
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
        }
    }

    private void initSelector() throws IOException, InterruptedException
    {
        /*创建TCP通道*/
        SocketChannel sc = SocketChannel.open();
        /*设置通道为非阻塞*/
        sc.configureBlocking(false);
        /*创建选择器*/
        selector = Selector.open();
        /*向选择器注册通道*/
        sc.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, new Buffers(256, 256));
        /*向服务器发起连接,一个通道代表一条tcp链接*/
        sc.connect(remoteAddress);
        /*等待三次握手完成*/
        while (!sc.finishConnect())
        {
            Thread.sleep(100);
        }
        System.out.println(remoteAddress + " " + "finished connection");
    }

    private void getSelection() throws IOException, InterruptedException
    {
        selector.select();
        /*Set中的每个key代表一个通道*/
        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
        /*遍历每个已就绪的通道，处理这个通道已就绪的事件*/
        while (it.hasNext())
        {
            SelectionKey key = it.next();
            /*防止下次select方法返回已处理过的通道*/
            it.remove();

            System.out.println("S exc read " + key.isReadable() + " write " + key.isWritable());

            /*表示底层socket的读缓冲区有数据可读*/
            if (key.isReadable())
            {
                /*从socket的读缓冲区读取到程序定义的缓冲区中*/
                SelectionKeyUtils.readChannel(key);
            }

            /*socket的写缓冲区可写*/
            if (key.isWritable())
            {
                SelectionKeyUtils.writeChannel(key, "发送消息！");
            }
        }
        Thread.sleep(1000 + new Random().nextInt(1000));
    }
}
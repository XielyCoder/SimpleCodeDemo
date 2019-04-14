package com.xiely.common;

import com.xiely.common.nio.TCPNonBlockClientSocket;

public class TcpClient
{
    public static void main(String[] args) throws InterruptedException
    {
        Thread thread = new Thread(new TCPNonBlockClientSocket(8080));
        thread.start();
        Thread.sleep(10000);
        thread.interrupt();
    }
}

package com.xiely.common.nio;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class FileOperator
{
    //allocate非直接缓冲区。
    //allocateDirect直接缓冲区，IO速度比非直接缓冲区快，回收该对象花费的时间比较多。
    //pos起始位置，lim结束位置，读模式下，两参数之间为可用空间，写模式下，两参数之间为已用空间。
    public static String readMsgFromFile(String filePath, String charset, int bufferSize)
    {
        File file = new File(filePath);
        if (!file.exists())
        {
            return StringUtils.EMPTY;
        }
        //读文件流的时候，文件必须存在。
        try (FileInputStream fis = new FileInputStream(file); FileChannel fc = fis.getChannel())
        {
            CharsetDecoder decoder = Charset.forName(charset).newDecoder();
            ByteBuffer bb = ByteBuffer.allocate(bufferSize);
            CharBuffer cb = CharBuffer.allocate(bufferSize);
            StringBuilder builder = new StringBuilder();
            while (fc.read(bb) != -1)
            {
                bb.flip();
                decoder.decode(bb, cb, false);
                cb.flip();
                builder.append(Arrays.copyOf(cb.array(), cb.limit()));
                cb.clear();
                bb.clear();
            }
            return builder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public static void writeMsgToFile(String msg, String filePath, String charset, int bufferSize)
    {
        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file); FileChannel fc = fos.getChannel())
        {
            ByteBuffer bb = ByteBuffer.allocate(bufferSize);
            byte[] msgBytes = msg.getBytes(charset);
            for (int i = 0; i < msgBytes.length; i += bufferSize)
            {
                bb.put(Arrays.copyOfRange(msgBytes, i, i + bufferSize));
                bb.flip();
                //文件不存在会创建。
                fc.write(bb);
                bb.clear();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

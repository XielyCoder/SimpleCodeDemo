package com.xiely.common.nio.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class BufferUtils
{
    private static Charset utf8 = Charset.forName("UTF-8");

    @SuppressWarnings("unused")
    public static ByteBuffer encode(CharBuffer charBuffer)
    {
        /*array()返回的就是内部的数组引用，编码以后的有效长度是0~limit*/
        return utf8.encode(charBuffer);
    }

    public static CharBuffer decode(ByteBuffer readBuffer)
    {
        /*array()返回的就是内部的数组引用，编码以后的有效长度是0~limit*/
        return utf8.decode(readBuffer);
    }
}

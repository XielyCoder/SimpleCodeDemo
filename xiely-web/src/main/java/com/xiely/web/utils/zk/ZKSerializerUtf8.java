package com.xiely.web.utils.zk;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.nio.charset.StandardCharsets;

public class ZKSerializerUtf8 implements ZkSerializer
{
    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError
    {
        String d = (String)data;
        return d.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError
    {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}


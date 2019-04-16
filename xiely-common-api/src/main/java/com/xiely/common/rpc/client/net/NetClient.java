package com.xiely.common.rpc.client.net;

import com.xiely.common.rpc.discovery.ServiceInfo;

public interface NetClient {
	byte[] sendRequest(byte[] data, ServiceInfo sinfo);
}

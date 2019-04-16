package com.xiely.common.zk.kept;

import java.io.IOException;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import net.killa.kept.KeptMap;

/**
 * KeptMap
 * 
 */
public class KeptMapDemo {
	
	public static void main(String[] args) {
		try {
			Watcher watcher = new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					System.out.println("接收到了WatchedEvent changed path: " + event.getPath()
							+ "; changed type: " + event.getType().name());
				}
			};
			ZooKeeper zk = new ZooKeeper("localhost:2181", 20000, watcher);
			Map<String, String> map = new KeptMap(zk, "/mymap", Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			map.put("hash", "map");
			Thread.sleep(300L);
			
			System.out.println(map.get("hash"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}


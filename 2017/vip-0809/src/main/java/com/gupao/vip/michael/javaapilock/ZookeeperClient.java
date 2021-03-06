package com.gupao.vip.michael.javaapilock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 *
 * 创建会话
 */
public class ZookeeperClient {

    private final static String CONNECTSTRING="192.168.251.221:2181,192.168.251.124:2181," +
            "192.168.251.122:2181,192.168.251.11:2181";

    private static int sessionTimeout=5000;

    //获取连接
    public static ZooKeeper getInstance() throws IOException, InterruptedException {
        final CountDownLatch conectStatus=new CountDownLatch(1);
        // lambda表达式下的匿名类，效果同下方代码
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTRING, sessionTimeout, (event)->{
            if(event.getState()== Watcher.Event.KeeperState.SyncConnected){
                conectStatus.countDown();
            }
        });
        /*ZooKeeper zooKeeper=new ZooKeeper(CONNECTSTRING, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                if(event.getState()== Event.KeeperState.SyncConnected){
                    conectStatus.countDown();
                }
            }
        });*/
        conectStatus.await();
        return zooKeeper;
    }

    public static int getSessionTimeout() {
        return sessionTimeout;
    }
}

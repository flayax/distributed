package com.gupao.vip.michael.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CuratorEventDemo {

    /**
     * 三种watcher来做节点的监听
     * pathcache   监视一个路径下子节点的创建、删除、节点数据更新
     * NodeCache   监视一个节点的创建、更新、删除
     * TreeCache   pathcaceh+nodecache 的合体（监视路径下的创建、更新、删除事件），
     * 缓存路径下的所有子节点的数据
     */

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework=CuratorClientUtils.getInstance();

        /**
         * 节点变化NodeCache
         */
        /*NodeCache cache=new NodeCache(curatorFramework,"/curator",false);
        cache.start(true);
        // lambda表达式下的匿名类，效果同下方代码
        cache.getListenable().addListener(()-> System.out.println("节点数据发生变化,变化后的结果" +
                "："+new String(cache.getCurrentData().getData())));
//        cache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("节点数据发生变化,变化后的结果" + new String(cache.getCurrentData().getData()));
//            }
//        });

        curatorFramework.setData().forPath("/curator","789".getBytes());*/


        /**
         * PatchChildrenCache
         * 构建/event子节点监听
         */
        PathChildrenCache cache=new PathChildrenCache(curatorFramework,"/event",true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        // 添加节点监听策略
        cache.getListenable().addListener((curatorFramework1,pathChildrenCacheEvent)->{
            switch (pathChildrenCacheEvent.getType()){
                case CHILD_ADDED:
                    System.out.println("增加子节点");
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点");
                    break;
                case CHILD_UPDATED:
                    System.out.println("更新子节点");
                    break;
                default:break;
            }
        });
        // 检测检点是否存在
        Stat eventNodeStat = curatorFramework.checkExists().forPath("/event");
        if(eventNodeStat == null) {
            curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/event","event".getBytes());
            TimeUnit.SECONDS.sleep(1);
            System.out.println("1");
        }
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/event/event1","1".getBytes());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("2");
        curatorFramework.setData().forPath("/event/event1","99".getBytes());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("3");
        curatorFramework.delete().forPath("/event/event1");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("4");
        curatorFramework.delete().forPath("/event");
        System.out.println("5");

        System.in.read();

    }
}

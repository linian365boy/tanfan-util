package com.nian.util;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Hello world!
 *
 */
public class ZkSample {
    private static Logger logger = LoggerFactory.getLogger(ZkSample.class);

    private static class ZkChildListener implements IZkChildListener{
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
            logger.info("childChange parentPath|{}, currentChilds|{}",parentPath, currentChilds.toString());
        }
    }

    private static class ZkDataListener implements IZkDataListener{
        public void handleDataChange(String s, Object o) throws Exception {
            logger.info("ZkDataListener handleDataChange|s value = {}, o = {}",s,o);
        }

        public void handleDataDeleted(String s) throws Exception {
            logger.info("ZkDataListener handleDataDeleted|s value = {}, o = {}",s);
        }
    }

    private static class ZkStateListener implements IZkStateListener{
        public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
            logger.info("ZkStateListener handleStateChanged|keeperState = {}", keeperState);
        }

        public void handleNewSession() throws Exception {
            logger.info("ZkStateListener handleNewSession");
        }

        public void handleSessionEstablishmentError(Throwable throwable) throws Exception {
            logger.info("ZkStateListener handleSessionEstablishmentError|throwable value = {}",throwable);
        }
    }

    public static void test1(ZkClient client, String path){
        Stat stat = new Stat();
        //获取节点中的对象
        User user2 = client.readData(path, stat);
        System.out.println(" user2 name "+user2.getName());
        System.out.println(" zk stat "+stat);

        boolean e = client.exists("/testUserNode/nima");
        if(!e){
            String path2 = client.create("/testUserNode/nima", "String data ok ..", CreateMode.PERSISTENT);
            //输出创建节点的路径
            System.out.println("create path: " + path2);
        }

        Stat stat1 = new Stat();
        Object obj = client.readData("/testUserNode", stat1);
        System.out.println(" zk stat1 "+stat1);

        User user3 = new User();
        user3.setId(2);
        user3.setName("niange2");
        client.writeData("/testUserNode", user3);

        Stat stat2 = new Stat();
        client.readData("/testUserNode", stat2);
        System.out.println(" zk stat2 "+stat2);

        List<String> children = client.getChildren("/testUserNode");
        for (String str : children){
            System.out.println(" child: "+str);
            Object object = client.readData("/testUserNode/"+str);
            System.out.println(" child object value : "+ object);
        }
        List<String> cs = client.getChildren("/testUserNode/test3");
        System.out.println(" testUserNode nima children size() eq  "+cs.size());
    }

    public static void testCreateChild(ZkClient client, String path, Object data){
        //true表示节点存在，false表示不存在
        boolean e = client.exists(path);
        if(!e) {
            String nodePath = client.create(path, data, CreateMode.PERSISTENT);
            //输出创建节点的路径
            //System.out.println("create path: " + nodePath);
            logger.info("create path :{}", nodePath);
        }
        client.delete(path);
    }


    public static void main(String[] args){
        ZkClient client = new ZkClient("192.168.128.128:2181", 1000);
        User user = new User();
        user.setId(2);
        user.setName("niange2");
        //子节点变化
        client.subscribeChildChanges("/testUserNode", new ZkChildListener());
        client.subscribeDataChanges("/testUserNode", new ZkDataListener());
        String path = "/testUserNode/test4";

        testCreateChild(client, path, user);

        //test1(client, path);
    }
}

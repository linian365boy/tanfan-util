package com.nian.util;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Hello world!
 *
 */
public class ZkSample {

    private static class ZkChildListener implements IZkChildListener{
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
            System.out.println("childChange parentPath-="+parentPath);
            System.out.println(currentChilds.toString());
        }
    }

    private static class ZkDataListener implements IZkDataListener{
        public void handleDataChange(String s, Object o) throws Exception {

        }

        public void handleDataDeleted(String s) throws Exception {

        }
    }

    private static class zk implements IZkStateListener{
        public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {

        }

        public void handleNewSession() throws Exception {

        }

        public void handleSessionEstablishmentError(Throwable throwable) throws Exception {

        }
    }


    public static void main(String[] args){
        ZkClient client = new ZkClient("192.168.139.159:2181", 1000);
        User user = new User();
        user.setId(1);
        user.setName("niange");
        client.subscribeChildChanges("/testUserNode", new ZkChildListener());
        String path = "/testUserNode";
        //true表示节点存在，false表示不存在
        boolean e = client.exists(path);
        if(!e) {
            String path2 = client.create(path, user, CreateMode.PERSISTENT);
            //输出创建节点的路径
            System.out.println("create path: " + path2);
        }
        Stat stat = new Stat();
        //获取节点中的对象
        User user2 = client.readData(path, stat);
        System.out.println(" user2 name "+user2.getName());
        System.out.println(" zk stat "+stat);

        /*boolean deleteFlag = client.delete(path);
        if(deleteFlag){
            System.out.println("delete success!");
        }else{
            System.out.println("delete fail!");
        }*/

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
    }
}

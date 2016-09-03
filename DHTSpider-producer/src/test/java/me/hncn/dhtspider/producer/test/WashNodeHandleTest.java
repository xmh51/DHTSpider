package me.hncn.dhtspider.producer.test;

import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.producer.dht.DHTClientList;
import me.hncn.dhtspider.producer.handle.PingHandle;
import me.hncn.dhtspider.producer.handle.WashNodeHandle;
import me.hncn.dhtspider.service.Node.NodeService;
import org.junit.Test;

/**
 * Created by XMH on 2016/7/17.
 */
public class WashNodeHandleTest extends SpringProducerTest{
    @Test
    public void aaa(){
        NodeService n = getBean(NodeService.class);
        System.out.println(n.count(new Node()));
    }
}

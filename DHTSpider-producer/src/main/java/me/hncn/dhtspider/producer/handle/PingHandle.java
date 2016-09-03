package me.hncn.dhtspider.producer.handle;

import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.producer.dht.DHTClient;
import me.hncn.dhtspider.producer.dht.DHTClientList;
import me.hncn.dhtspider.producer.dht.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XMH on 2016/7/14.
 */
@Component("pingHandle")
public class PingHandle {
    @Autowired
    private DHTClientList dhtClientList;
    @Autowired
    private Table table;
    private Logger logger = LoggerFactory.getLogger(PingHandle.class);

    public void handle() throws InterruptedException {
        Node[] nodes =table.getAll();
        Map<String,Object> content =new HashMap<>();
        logger.info("ping 任务开始");
        for(Node node:nodes){
            dhtClientList.sendDht("ping",node.getIp(),node.getPort(),content);
        }
        logger.info("ping 任务结束");
    }
}

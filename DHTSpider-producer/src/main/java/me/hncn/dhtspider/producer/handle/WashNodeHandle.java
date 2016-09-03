package me.hncn.dhtspider.producer.handle;

import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.producer.dht.Table;
import me.hncn.dhtspider.service.Node.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**清洗数据 清掉过时节点
 * Created by XMH on 2016/7/17.
 */
@Component("washNodeHandle")
public class WashNodeHandle {
    @Autowired
    private Table table;
    @Autowired
    private NodeService nodeService;
    private Logger logger = LoggerFactory.getLogger(WashNodeHandle.class);

    public void handle() throws InterruptedException {
        long now = System.currentTimeMillis();
        List<Node> nodes =nodeService.findAll();
        logger.info("washNodeHandle 任务开始");
        List<Node> removeList = new ArrayList<>();
        Map<String,Node> map = new HashMap<>();
        int offLine = 0;
        int repeatIpAndPort=0;
        if(nodes.size()>310000){
            int l = nodes.size()-310000;
            for(int i = 110000;i<220000+l;i++){
                Node node = nodes.get(i);
                removeList.add(node);
            }
            logger.info("移除过多节点 本次 共删除 {}",removeList.size());
        }else {
            for(Node node:nodes){
                if(nodes.size()-removeList.size()>50000&&node.getCreateTime()!=null){ //如果节点数低于5万 则不移除节点
                    if((now-node.getCreateTime().getTime())>1000*60*60*5){//创建时间大于12小时
                        //更新时间不存在或者更新时间晚8小时
                        String key = node.getIp()+node.getPort();
                        if(node.getUpdateTime()==null||(now-node.getUpdateTime().getTime()>1000*60*60*3)){
                            offLine++;
                            removeList.add(node);
                        }else if(map.get(key)!=null){
                            repeatIpAndPort++;
                            removeList.add(node);
                        }

                        map.put(key,node);
                    }
                }
                if(nodes.size()-removeList.size()<=50000){
                    break;
                }
            }
            logger.info("移除离线重复 node 共移除{}个节点,其中 离线节点 {} ip port 重复节点 {}",removeList.size(),offLine,repeatIpAndPort);
        }
        nodeService.delete(removeList);
        table.refresh();
        logger.info("washNodeHandle 任务结束");
    }
}

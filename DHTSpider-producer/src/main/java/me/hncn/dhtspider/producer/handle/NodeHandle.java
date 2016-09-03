package me.hncn.dhtspider.producer.handle;

import me.hncn.dhtspider.model.mongo.infoHash.InfoHash;
import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.service.Node.NodeService;
import me.hncn.dhtspider.service.infoHash.InfoHashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by XMH on 2016/7/17.
 */
@Component("nodeHandle")
public class NodeHandle {
    @Autowired
    private NodeService nodeService;
    private long addSize=0;
    private long updateSize=0;
    private long totalSize=0;
    private BlockingQueue<Node> waitAdd =new ArrayBlockingQueue<>(8000);
    private static Logger logger = LoggerFactory.getLogger(NodeHandle.class);
    public NodeHandle(){
        init();
    }

    private void init() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    logger.info("本轮 共处理 {} node ,update {} add {} 待处理  {} 个 node to db",totalSize,updateSize,addSize,waitAdd.size());
                    addSize=0;
                    totalSize=0;
                    updateSize=0;
                    long count = nodeService.count(new Node());
                    logger.info("total node {} from db",count);
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, 1, 1000*60*5);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 2, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 3, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 4, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 5, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 6, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 7, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 8, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 9, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 10, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 11, 10);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        Node node  = waitAdd.poll();
                        if(node!=null){
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node to db error  {}", e.getMessage());
                }
            }
        }, 11, 10);
    }

    public void addToQueue(Node node){
        try {
            waitAdd.put(node);
        } catch (InterruptedException e) {
            logger.error("addToQueue node error {}",e.getMessage());
        }
    }

    private void add( Node node){
        totalSize++;
        if(node.getId()==null){
            addSize++;
        }else {
            updateSize++;
        }
        nodeService.saveOrUpdate(node);
    }
}

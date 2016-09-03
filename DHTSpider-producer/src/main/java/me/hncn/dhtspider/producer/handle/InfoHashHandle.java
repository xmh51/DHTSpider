package me.hncn.dhtspider.producer.handle;

import me.hncn.dhtspider.model.mongo.infoHash.InfoHash;
import me.hncn.dhtspider.model.mongo.node.Node;
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
@Component("infoHashHandle")
public class InfoHashHandle {
    @Autowired
    private InfoHashService infoHashService;
    private long addSize=0;
    private BlockingQueue<byte[]> waitAdd =new ArrayBlockingQueue<>(50000);
    private static Logger logger = LoggerFactory.getLogger(InfoHashHandle.class);
    public InfoHashHandle(){
        init();
    }

    private void init() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    logger.info("本轮 共处理 {} info_hash 待处理  {} 个 info_hash to db",addSize,waitAdd.size());
                    addSize=0;
                    logger.info("total info_hash {} from db",infoHashService.count(new InfoHash()));
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 1, 1000*60*5);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 2, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 3, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                    } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 4, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 5, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 6, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 7, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 8, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 9, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 10, 50);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size()>0){
                        byte[] infoHashBytes  = waitAdd.poll();
                        if(infoHashBytes!=null){
                            add(infoHashBytes);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add info_hash error {}", e.getMessage());
                }
            }
        }, 11, 50);
    }

    public void addToQueue(final  byte[] infoHashBytes){
        try {
            waitAdd.put(infoHashBytes);
        } catch (InterruptedException e) {
            logger.error("addToQueue info_hash error {}",e.getMessage());
        }
    }

    private void add( byte[] infoHashBytes){
        addSize++;
        infoHashService.insertByInfoHashBytes(infoHashBytes);
    }
}

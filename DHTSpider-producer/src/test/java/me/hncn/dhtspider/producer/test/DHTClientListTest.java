package me.hncn.dhtspider.producer.test;

import me.hncn.dhtspider.producer.dht.DHTClientList;
import me.hncn.dhtspider.producer.handle.PingHandle;
import org.junit.Test;

/**
 * Created by XMH on 2016/7/17.
 */
public class DHTClientListTest extends SpringProducerTest {
    @Test
    public void aaa(){
        DHTClientList dhtClientList = getBean(DHTClientList.class);
        PingHandle pingHandle = getBean(PingHandle.class);
        try {
            pingHandle.handle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

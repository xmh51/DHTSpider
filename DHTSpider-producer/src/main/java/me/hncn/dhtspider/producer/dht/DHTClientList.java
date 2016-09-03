package me.hncn.dhtspider.producer.dht;

import me.hncn.dhtspider.model.mongo.infoHash.InfoHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XMH on 2016/7/17.
 */
public class DHTClientList {
    private Integer portStart;
    private Integer size;
    private Table table;
    private List<DHTClient> dhtClients = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(DHTClientList.class);
    public void init(){
        for(int i = 0;i<size;i++){
            try {
                DHTClient dhtClient= new DHTClient(table,portStart+i);
                dhtClients.add(dhtClient);
            } catch (IOException e) {
               logger.error(e.getMessage());
            }
        }
    }
    public void setPortStart(Integer portStart) {
        this.portStart = portStart;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<DHTClient> getDhtClients() {
        return dhtClients;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void sendDht(String type, String ip, int port, Map<String, Object> content) {
        for(DHTClient dhtClient:dhtClients){
            dhtClient.sendDht(type,ip,port,content);
        }
    }
}

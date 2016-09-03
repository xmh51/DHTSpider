package me.hncn.dhtspider.producer.test;

import me.hncn.dhtspider.model.mongo.infoHash.InfoHash;
import me.hncn.dhtspider.producer.dht.DHTClient;
import me.hncn.dhtspider.service.infoHash.InfoHashService;
import me.hncn.dhtspider.spring.SpringContextHolder;
import me.hncn.dhtspider.util.ByteUtil;
import me.hncn.dhtspider.util.RandomUtil;
import me.hncn.dhtspider.util.ShaUtil;
import org.junit.Test;

import java.util.List;

/**
 * Created by XMH on 2016/7/13.
 */
public class InfoHashTest extends SpringTest {
    @Test
    public void insert(){
        InfoHashService infoHashService = SpringContextHolder.getBean(InfoHashService.class);
        infoHashService.insertByInfoHashBytes(ByteUtil.getRanDomBytes(20));
    }
    @Test
    public void delete(){
        InfoHashService infoHashService = SpringContextHolder.getBean(InfoHashService.class);
        infoHashService.delete("578637c484fceaadd53b5d08");
    }

    @Test
    public void deleteAll(){
        InfoHashService infoHashService = SpringContextHolder.getBean(InfoHashService.class);
        infoHashService.deleteAll();
    }


    @Test
    public void findAll(){
        InfoHashService infoHashService = SpringContextHolder.getBean(InfoHashService.class);
        List<InfoHash> infoHashList  =infoHashService.findAll();
        for(InfoHash infoHash :infoHashList){
            infoHash.setInfoHashHex(infoHash.getInfoHashHex().toUpperCase());
            infoHashService.save(infoHash);
        }
    }
    @Test
    public void dsd(){
        String hex= "d1ccb86315e7ff33c4bf7dae425abff7cc179908";
        byte[] bytes1 = ShaUtil.hexStringToBytes(hex);
                hex= hex.toUpperCase();
        byte[] bytes = bytes1 = ShaUtil.hexStringToBytes(hex);
    }
}

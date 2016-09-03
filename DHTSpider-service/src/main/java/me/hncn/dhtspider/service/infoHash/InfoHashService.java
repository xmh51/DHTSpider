package me.hncn.dhtspider.service.infoHash;

import me.hncn.dhtspider.model.mongo.infoHash.InfoHash;
import me.hncn.dhtspider.repository.mongodb.dao.infoHash.InfoHashDao;
import me.hncn.dhtspider.util.ShaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by XMH on 2016/7/13.
 */
@Service
public class InfoHashService {
    @Autowired
    private InfoHashDao infoHashDao;
    Logger logger = LoggerFactory.getLogger(InfoHashService.class);
    public synchronized InfoHash insertByInfoHashBytes(byte[] infoHashBytes){
        try {
            String infoHashHex = ShaUtil.byteToHexString(infoHashBytes);

            InfoHash infoHash = infoHashDao.findByInfoHashHex(infoHashHex);
            if(infoHash!=null){
                return null;
            }

            infoHash = new InfoHash();
            infoHash.setInfoHashBytes(infoHashBytes);
            infoHash.setInfoHashHex(infoHashHex);
            infoHash.setCreateTime(new Date());
            infoHashDao.insert(infoHash);
            return infoHash;
        }catch (Exception e){
            logger.error("insertByInfoHashBytes error {}",e.getMessage());
        }
        return null;
    }

    public void delete(String id) {
        infoHashDao.delete(id);
    }

    public List<InfoHash> findAll() {
        return infoHashDao.findAll();
    }

    public void deleteAll() {
        infoHashDao.deleteAll();
    }

    public void save(InfoHash infoHash) {
        infoHashDao.save(infoHash);
    }

    public long count(InfoHash infoHash) {
       return infoHashDao.count(Example.of(infoHash));
    }
}

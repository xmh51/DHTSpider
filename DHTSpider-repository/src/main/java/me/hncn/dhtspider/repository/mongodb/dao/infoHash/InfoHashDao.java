package me.hncn.dhtspider.repository.mongodb.dao.infoHash;

import me.hncn.dhtspider.model.mongo.infoHash.InfoHash;
import me.hncn.dhtspider.repository.mongodb.dao.base.mongodbBaseRepository.MongodbBaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by XMH on 2016/7/13.
 */
@Repository("infoHashDao")
public interface InfoHashDao extends MongodbBaseRepository<InfoHash,String> {
    InfoHash findByInfoHashHex(String infoHashHex);
}

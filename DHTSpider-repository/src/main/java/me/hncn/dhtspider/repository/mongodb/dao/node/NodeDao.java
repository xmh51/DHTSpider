package me.hncn.dhtspider.repository.mongodb.dao.node;

import me.hncn.dhtspider.repository.mongodb.dao.base.mongodbBaseRepository.MongodbBaseRepository;
import me.hncn.dhtspider.model.mongo.node.Node;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by 001 on 2016/7/8.
 */
@Repository("nodeDao")
public interface NodeDao extends MongodbBaseRepository<Node,String> {
    Node findByNodeIdHex(String nodeIdHex);

    void deleteByIp(String ip);
}

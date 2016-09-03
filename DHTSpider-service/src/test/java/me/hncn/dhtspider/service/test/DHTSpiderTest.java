package me.hncn.dhtspider.service.test;

import me.hncn.dhtspider.repository.mongodb.dao.node.NodeDao;
import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.service.Node.NodeService;
import me.hncn.dhtspider.util.ShaUtil;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by 001 on 2016/7/8.
 */
public class DHTSpiderTest extends SpringTest {


    @Test
    public void nodeTest() throws UnsupportedEncodingException {
        NodeService nodeService = getBean(NodeService.class);
        Node node = new Node();
        List<Node> nodeList = nodeService.findListSortLimit(node, 100000, Sort.Direction.DESC,"create_time");
        System.out.println(nodeList.size());

    }
}

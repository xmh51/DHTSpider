package me.hncn.dhtspider.producer.test;

import com.fasterxml.jackson.core.type.TypeReference;
import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.producer.dht.DHTClient;
import me.hncn.dhtspider.producer.dht.DHTClientList;
import me.hncn.dhtspider.producer.dht.Table;
import me.hncn.dhtspider.repository.mongodb.dao.node.NodeDao;
import me.hncn.dhtspider.service.Node.NodeService;
import me.hncn.dhtspider.spring.SpringContextHolder;
import me.hncn.dhtspider.util.ByteUtil;
import me.hncn.dhtspider.util.ShaUtil;
import me.hncn.util.json.JsonUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by 001 on 2016/7/8.
 */
public class DHTSpiderTest extends SpringTest {


    @Test
    public void nodeTest() throws IOException {

        Table table = getBean(Table.class);
        DHTClientList dhtClientList = getBean(DHTClientList.class);


            int i =0;
            while (table.size() < 10000) {
              try{
                  Map<String, Object> content = new HashMap<>();
                 // content.put("target", dhtClient.getRandomNodeId());

                  for (Node node : table.getAll()) {
                      dhtClientList.sendDht("find_node", node.getIp(), node.getPort(), content);
                      Thread.sleep(20);
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }


                // dhtClient.sendDht("ping", "127.0.0.1", 17785, new HashMap<>());
                //dhtClient.sendDht("ping", "127.0.0.1", 17784, new HashMap<>());
                i++;
            }
    }
    @Test
    public void removeAll(){
        NodeService nodeService = SpringContextHolder.getBean(NodeService.class);
        nodeService.deleteAll();
    }

    @Test
    public void remove(){
        NodeService nodeService = SpringContextHolder.getBean(NodeService.class);
        nodeService.deleteByIp("127.0.0.1");
    }
    @Test
    public void find(){
        NodeDao nodeDao = SpringContextHolder.getBean(NodeDao.class);
        Node node = nodeDao.findByNodeIdHex("f5eb675a2b6905a21f3fd8a3e77d53bafb9c76d6");
        System.out.println(node);
    }

    @Test
    public void findAll(){
        NodeDao nodeDao = SpringContextHolder.getBean(NodeDao.class);
        List<Node> nodes = nodeDao.findAll();
        for(Node node :nodes){
            node.setNodeIdHex(node.getNodeIdHex().toUpperCase());
            nodeDao.save(node);
        }
    }
    @Test
    public void random(){
        NodeService nodeService = SpringContextHolder.getBean(NodeService.class);
        List<Node> nodes = nodeService.findAll();
        for(Node node:nodes){
            System.out.println(node);
            System.out.println(node.getNodeIdHex().equals(ShaUtil.byteToHexString(node.getNodeIdOrgByte())));
        }
        String hex = ShaUtil.byteToHexString(DHTClient.getRandomNodeId());
        System.out.println(hex + " length"+hex.length());
    }

    @Test
    public void clare(){
        NodeService nodeService = SpringContextHolder.getBean(NodeService.class);
        List<Node> nodes = nodeService.findAll();
        Map<String,Node> map = new HashMap<>();
        for(Node node:nodes){
           map.put("ip"+node.getIp()+"port"+node.getPort(),node);
        }
        nodeService.deleteAll();
        for (String key : map.keySet()) {
            Node node = map.get(key);
            nodeService.saveOrUpdate(node);
        }

    }
    @Test
    public void addAll(){
        Table table = new Table();

        String json = "[{\"id\":\"5784800e973cc1d22a5d5054\",\"nodeIdHex\":\"867a647035217ce6a2a283a38860ac5d5eee64fc\",\"nodeIdOrgByte\":\"hnpkcDUhfOaiooOjiGCsXV7uZPw=\",\"ip\":\"121.128.58.212\",\"port\":51413,\"status\":1},{\"id\":\"57848079973cc1d22a5d5056\",\"nodeIdHex\":\"c98d61f8144a49340fa721285344b241eb50b8db\",\"nodeIdOrgByte\":\"yY1h+BRKSTQPpyEoU0SyQetQuNs=\",\"ip\":\"93.152.202.181\",\"port\":6881,\"status\":1},{\"id\":\"57847eab973ce7756b5ad834\",\"nodeIdHex\":\"cc9ef3c758072b3d9f6ed113cfaf39e48493b901\",\"nodeIdOrgByte\":\"zJ7zx1gHKz2fbtETz6855ISTuQE=\",\"ip\":\"185.34.3.244\",\"port\":10124,\"status\":1},{\"id\":\"57847c9a973c51b21cc067c2\",\"nodeIdHex\":\"d9f00d9ea93745885982862d07779480a3f5a647\",\"nodeIdOrgByte\":\"2fANnqk3RYhZgoYtB3eUgKP1pkc=\",\"ip\":\"121.161.87.214\",\"port\":51413,\"status\":1},{\"id\":\"57848037973cc1d22a5d5055\",\"nodeIdHex\":\"de0104d91f6c266ba14a2bbca4c76d29af5ddca7\",\"nodeIdOrgByte\":\"3gEE2R9sJmuhSiu8pMdtKa9d3Kc=\",\"ip\":\"192.95.26.221\",\"port\":51413,\"status\":1},{\"id\":\"57847fce973cc1d22a5d5053\",\"nodeIdHex\":\"ebff36697351ff4aec29cdbaabf2fbe3467cc267\",\"nodeIdOrgByte\":\"6/82aXNR/0rsKc26q/L740Z8wmc=\",\"ip\":\"82.221.103.244\",\"port\":6881,\"status\":1},{\"id\":\"57847c5b973c7a81822f7e8d\",\"nodeIdHex\":\"f5eb675a2b6905a21f3fd8a3e77d53bafb9c76d6\",\"nodeIdOrgByte\":\"9etnWitpBaIfP9ij531TuvucdtY=\",\"ip\":\"212.129.33.50\",\"port\":6881,\"status\":1},{\"id\":\"57847cb2973c51b21cc067c3\",\"nodeIdHex\":\"2171d63e134c8ae8d438be25d148f7bae7ddd329\",\"nodeIdOrgByte\":\"IXHWPhNMiujUOL4l0Uj3uufd0yk=\",\"ip\":\"1.244.254.188\",\"port\":50982,\"status\":1},{\"id\":\"57847e9f973ce7756b5ad833\",\"nodeIdHex\":\"32f54e697351ff4aec29cdbaabf2fbe3467cc267\",\"nodeIdOrgByte\":\"MvVOaXNR/0rsKc26q/L740Z8wmc=\",\"ip\":\"67.215.246.10\",\"port\":6881,\"status\":1}]";
        List<Node> nodes = new ArrayList<>();
        nodes= JsonUtil.getObj(json, new TypeReference<List<Node>>() {});
        table.addAll(nodes);
    }

    @Test
    public void Service1() throws IOException {
        Table table = new Table();
        DHTClient dhtClient = new DHTClient(table, 17783);
        try {
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000000000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    @Test
    public void Service2() throws IOException {
        Table table = new Table();
        DHTClient dhtClient = new DHTClient(table, 17784);
        try {
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000000000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Service3() throws IOException {
        Table table = new Table();
        DHTClient dhtClient = new DHTClient(table, 17785);
        try {
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000000000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

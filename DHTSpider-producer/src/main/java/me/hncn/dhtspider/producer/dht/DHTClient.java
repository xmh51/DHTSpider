package me.hncn.dhtspider.producer.dht;


import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.producer.handle.InfoHashHandle;
import me.hncn.dhtspider.producer.udp.UdpClientOrServer;
import me.hncn.dhtspider.producer.util.BEncodeUtil;
import me.hncn.dhtspider.service.Node.NodeService;
import me.hncn.dhtspider.service.infoHash.InfoHashService;
import me.hncn.dhtspider.spring.SpringContextHolder;
import me.hncn.dhtspider.util.ByteUtil;
import me.hncn.dhtspider.util.ShaUtil;
import me.hncn.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * DHTClient
 * Created by XMH on 2016/6/13.
 */
public class DHTClient extends UdpClientOrServer {
    Logger logger = LoggerFactory.getLogger(DHTClient.class);
    List<String> msgTypes;
    private Table table;
    private Node localNode;
    private BEncodeUtil bEncodeUtil = new BEncodeUtil();
    private InfoHashHandle infoHashHandle;

    {
        String[] msgTypeArray = {"ping", "find_node", "get_peers", "announce_peer"};
        msgTypes = Arrays.asList(msgTypeArray);
    }


    public DHTClient(Table table, String nodeIdHex, String localIp, Integer bindPort) throws IOException {
        super(localIp, bindPort);
        if (table == null) {
            table = new Table();
        }
        byte[] nodeId;
        if (nodeIdHex == null) {
            nodeId = getRandomNodeId();
        }else {
            nodeId= ShaUtil.hexStringToBytes(nodeIdHex);
        }
        this.table = table;
        localNode = new Node(nodeId, null, bindPort);
        infoHashHandle = SpringContextHolder.getBean(InfoHashHandle.class);
    }


    public DHTClient(Table table, Integer bindPort) throws IOException {
        this(table, null, null, bindPort);
    }

    public DHTClient(Table table, String nodeIdHex, Integer bindPort) throws IOException {
        this(table, nodeIdHex, null, bindPort);
    }


    public DHTClient(Integer bindPort) throws IOException {
        this(null, null, null, bindPort);
    }

    /**
     * 处理 接收的数据包
     * 在实践观察中发现两种新的消息
     * 1.vote:表示客户端发送速度过快，希望可以减慢
     * 2.v:表示客户端的版本号
     *
     * @param address 远程 udp 地址
     * @param receive 收到的数据
     * @throws UnsupportedEncodingException
     */
    @Override
    public void handleReceive(InetSocketAddress address, byte[] receive) throws UnsupportedEncodingException {
        Map<String, Object> receiveMap = filterDHTEncode(receive);
        logger.debug("接收udp包 {} {} {}", JsonUtil.getJsonStr(receiveMap), address.getAddress().getHostAddress(), address.getPort());
        handleReceiveMap(address, receiveMap, receive);
    }

    /**
     * ping请求={"t":"aa", "y":"q","q":"ping", "a":{"id":"abcdefghij0123456789"}}
     * 远程 dht ping
     *
     * @param host 远程 host
     * @param port 远程端口
     */
    public void sendDht(String type, String host, int port, Map<String, Object> content) {
        Map<String, Object> map = new HashMap<>();
        map.put("t", ByteUtil.getRanDomBytes(2));
        map.put("y", "q");
        map.put("q", type);
        content.put("id", localNode.getNodeIdOrgByte());
        map.put("a", content);
        sendMsgByUdp(map, host, port);

    }

    /**
     * 发送 dht udp 包
     *
     * @param map  发送数据
     * @param host 地址
     * @param port 端口
     */
    public void sendMsgByUdp(Map<String, Object> map, String host, int port) {
        logger.debug("发送 Udp {} {} {}", JsonUtil.getJsonStr(map), host, port);
        super.sendMsgByUdp(bEncodeUtil.encode(map), host, port);
    }

    /**
     * 过滤 接收的数据包
     *
     * @param receive
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, Object> filterDHTEncode(byte[] receive) throws UnsupportedEncodingException {
        Map<String, Object> obj = (Map<String, Object>) bEncodeUtil.decode(receive);
        if (obj != null) {
                return obj;

        }
        return null;

    }

    /**
     * 处理 接收 信息
     *
     * @param address    网络 地址
     * @param receiveMap 收到的 信息
     * @return String 响应
     */
    private void handleReceiveMap(InetSocketAddress address, Map<String, Object> receiveMap, byte[] receive) throws UnsupportedEncodingException {


        if (receiveMap != null) {
            String y = new String((byte[]) receiveMap.get("y"));
            if ("r".equals(y)) {
                doC(address, receiveMap, receive);
            }
            if ("q".equals(y)) {
                doS(address, receiveMap, receive);
            }
        }
    }

    /**
     * 处理 接收数据包 服务器 模式
     * 处理 dht 网络 主动 请求信息 {"ping", "find_node", "get_peers", "announce_peer"};
     *
     * @param receiveMap 收到的 信息
     * @param address    网络 地址
     * @return String 响应
     */
    private void doS(InetSocketAddress address, Map<String, Object> receiveMap, byte[] receive) {
        String q = new String((byte[]) receiveMap.get("q"));
        Map<String, Object> a = (Map<String, Object>) receiveMap.get("a");
        byte[] idOrg = (byte[]) a.get("id");
        addNode(address, idOrg);
        Map<String, Object> baseRespMap = baseRespMap(receiveMap);
        Map<String, Object> r = (Map<String, Object>) baseRespMap.get("r");

        if ("find_node".equals(q)) {
            byte[] target = (byte[]) a.get("target");

            r.put("nodes", table.getNodesByNodeId(target));
            // do some

        }
        if ("get_peers".equals(q)) {
            byte[] infoHashBytes = (byte[]) a.get("info_hash");
            if (infoHashBytes.length == 20) {
                infoHashHandle.addToQueue(infoHashBytes);
            }
            r.put("nodes", table.getNodesByNodeId(infoHashBytes));
            r.put("token", getRandomToken());
            // do some

        }
        if ("announce_peer".equals(q)) {
            //原则上需要检验 token
            // do some

        }
        logger.debug("doS 类型 {} 服务器模式响应内容   {} {} {}", q,baseRespMap, address.getAddress().getHostAddress(), address.getPort());
        sendMsgByUdp(baseRespMap, address.getAddress().getHostAddress(), address.getPort());

    }

    /**
     * 处理 接收数据包 客户端 模式
     *
     * @param address    网络 地址
     * @param receiveMap 收到的 信息
     */
    private void doC(InetSocketAddress address, Map<String, Object> receiveMap, byte[] receive) throws UnsupportedEncodingException {
        Map<String, Object> r = (Map<String, Object>) receiveMap.get("r");
        byte[] idOrg = (byte[]) r.get("id");
        addNode(address, idOrg);
        if (r.get("nodes") != null) {
            List<Node> nodeList = getNodesInfo((byte[]) r.get("nodes"));
            sendNodes(nodeList);
        }
    }

    /**
     * dht 基本 响应 格式
     *
     * @param receiveMap 收到的 信息
     * @return 基本 响应 格式信息
     */
    private Map<String, Object> baseRespMap(Map<String, Object> receiveMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("t", receiveMap.get("t"));
        map.put("y", "r");
        Map<String, Object> r = new HashMap<>();
        r.put("id", localNode.getNodeIdOrgByte());
        map.put("r", r);
        return map;
    }

    private void addNode(InetSocketAddress address, byte[] idOrg) {
        try {
            Node node = new Node(idOrg, address.getAddress().getHostAddress(), address.getPort());
            node.setStatus(1);
            table.addToQueue(node);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void sendNodes(List<Node> nodeList) {
        if (table.size() < table.maxSize) {
            for (Node node : nodeList) {
                Map<String, Object> content = new HashMap<>();
                sendDht("ping", node.getIp(), node.getPort(), content);
            }

        }

    }

    public Node getLocalNode() {
        return localNode;
    }

    private static String long2IpAdress(long src) {
        long l = 256 * 256 * 256;
        StringBuffer stringBuffer = new StringBuffer();
        while (l > 0) {
            stringBuffer.append(src / l).append(".");
            src = src % l;
            l /= 256;
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(2 % 26);
    }

    public List<Node> getNodesInfo(byte[] nodesInfo) throws UnsupportedEncodingException {
        List<Node> result = new ArrayList<Node>();

        for (int i = 0; i < nodesInfo.length / 26; i++) {

            byte[] n = new byte[26];
            System.arraycopy(nodesInfo, i * 26, n, 0, 26);
            result.add(NodeService.getNodeByCompactnodeInfobytes(n));
        }


        return result;
    }

    /*public List<Node> getNodesInfoTest(byte[] nodesInfo) throws UnsupportedEncodingException {
        List<Node> result = new ArrayList<Node>();

        for (int
             i = 0; i < nodesInfo.length / 26; i++) {
            Node node;
            byte[] nodeId = new byte[20];
            String nodeIp;
            byte[] nodeIpBytes = new byte[4];
            int nodePort;
            byte[] nodePortBytes = new byte[2];
            for (int j = i * 26; j < (i + 1) * 26; j++) {
                if (j % 26 <= 19) {
                    nodeId[j % 26] = nodesInfo[j];
                }
                if (19 < j % 26 &&
                        j % 26 <= 23) {
                    nodeIpBytes[j % 26 - 19 - 1] = nodesInfo[j];
                }
                if (23 < j % 26 &&
                        j % 26 <= 25) {
                    nodePortBytes[j % 26 - 23 - 1] = nodesInfo[j];
                }
            }
            long ip_temp = Long.parseLong(ShaUtil.byteToHexString(nodeIpBytes), 16);
            nodeIp = long2IpAdress(ip_temp);
            nodePort = Integer.parseInt(ShaUtil.byteToHexString(nodePortBytes), 16);
            node = new Node(nodeId, nodeIp, nodePort);
            result.addToQueue(node);
        }



        return result;
    }*/
    public static byte[] getRandomNodeId() {
        return ByteUtil.getRanDomBytes(20);
    }

    public static byte[] getRandomToken() {
        return ByteUtil.getRanDomBytes(5);
    }
}

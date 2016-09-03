package me.hncn.dhtspider.service.Node;

import com.mongodb.WriteResult;
import me.hncn.dhtspider.repository.mongodb.dao.node.NodeDao;
import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.util.ByteUtil;
import me.hncn.dhtspider.util.ShaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by XMH on 2016/7/11.
 */
@Service("nodeService")
public class NodeService {
    @Autowired
    private NodeDao nodeDao;
    Logger logger = LoggerFactory.getLogger(NodeService.class);

    public List<Node> findAll() {
        return nodeDao.findAll();
    }


    public Node saveOrUpdate(Node node) {
      try {

          if(node.getId()!=null){  //更新记录
              node.setUpdateTime(new Date());
              nodeDao.save(node);
          }else {
              Node old = nodeDao.findByNodeIdHex(node.getNodeIdHex());
              //插入记录
              if(old==null){
                  node.setCreateTime(new Date());
                  nodeDao.save(node);
              }else {
                  //更新记录
                  node.setId(old.getId());
                  node.setUpdateTime(new Date());
                  nodeDao.save(node);
              }

          }


          return node;
      }catch (Exception e){
          logger.error("saveOrUpdate error {}",e.getMessage());
      }
        return null;
    }
    public void deleteAll() {
         nodeDao.deleteAll();
    }

    public void delete(String id) {
        nodeDao.delete(id);
    }

    public void deleteByIp(String ip) {
        nodeDao.deleteByIp(ip);
    }
    public Node findOne(String id) {
        return nodeDao.findOne(id);
    }

    public long count(Node node){
        return nodeDao.count(Example.of(node));
    }
    public List<Node> findList(Node node) {
        return nodeDao.findAll();
    }

    public List<Node> findList(Node node,Pageable pageable) {
        Example<Node> example = Example.of(node);
        Page<Node> page = nodeDao.findAll(example,pageable);
        return page.getContent();
    }
    public List<Node> findListSortLimit(Node node,int limit,Sort.Direction direction,String sortProperties) {
        Pageable pageable =
                new PageRequest(0, limit, direction,sortProperties);
        Example<Node> example = Example.of(node);
        Page<Node> page = nodeDao.findAll(example,pageable);
        return page.getContent();
    }

    /**Peers的联系信息被编码为6字节的字符串.
     * 又被称为"CompactIP-address/port info"，
     * 其中前4个字节是网络字节序的IP地址，后2个字节是网络字节序的端口.
     * @return
     */
    private static byte[]  getCompactIPAddressAndPortInfo(Node node){


        byte[] bytes = new byte[6];
        String[] ips  =node.getIp().split("[.]");
        for(int i = 0;i<4;i++){
            Integer num = Integer.valueOf(ips[i]);
            if(num>127){
                num = num-256;
            }
            bytes[i] = num.byteValue();
        }
        String hexPort = Integer.toHexString(node.getPort());
        while (hexPort.length()!=4){ //当port 值过小时，前置补零，保证 16位长度
            hexPort = "0"+hexPort;
        }
        byte[] port = ShaUtil.hexStringToBytes(hexPort);
        System.arraycopy(port, 0, bytes, 4, 2);
        return bytes;
    }

    /**
     * Nodes的联系信息被编码为26字节的字符串.又被称为"Compactnode info"，其中前20字节是网络字节序的nodeID，后面6个字节是peers的"CompactIP-address/port info".
     * @return compactnodeInfo
     */
    public static byte[] getCompactnodeInfobytes(Node node){
        byte[] compactnodeInfo =new byte[26];
        System.arraycopy(node.getNodeIdOrgByte(), 0, compactnodeInfo, 0,20);
        System.arraycopy(getCompactIPAddressAndPortInfo(node), 0, compactnodeInfo, 20,6);
        return compactnodeInfo;
    }

    /**
     * Nodes的联系信息被编码为26字节的字符串.又被称为"Compactnode info"，其中前20字节是网络字节序的nodeID，后面6个字节是peers的"CompactIP-address/port info".
     * @return compactnodeInfo
     */
    public static Byte[] getCompactnodeInfoBytes(Node node){
        return ByteUtil.bytesToBytes(getCompactnodeInfobytes(node));
    }


    public static Node  getNodeByCompactnodeInfobytes(final byte[] n){
        Node node = null;



        byte[] nodeIdBytes = new  byte[20];
        System.arraycopy(n,0,nodeIdBytes,0,20);

        byte[] nodeIpBytes = new byte[4];
        System.arraycopy(n,20,nodeIpBytes,0,4);

        byte[] nodePortBytes = new byte[2];
        System.arraycopy(n,24,nodePortBytes,0,2);


        String nodeIp = getIpByCompactIPBytes(nodeIpBytes);
        Integer nodePort = Integer.parseInt(ShaUtil.byteToHexString(nodePortBytes), 16);
        try {
            node = new Node(nodeIdBytes, nodeIp, nodePort);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return node;
    }

    private static String getIpByCompactIPBytes(final byte[] ipBytes){
        String ip = "";
        for(int i =0;i<4;i++){
            int ipByte = ipBytes[i];
            if(ipByte<0){
                ipByte = ipByte+256;
            }
            ip = ip+ipByte;
            if(i!=3){
                ip = ip+".";
            }
        }
        return ip;
    }

    public void delete(List<Node> nodes) {
        nodeDao.delete(nodes);
    }

    public void updateStatus(List<Node> nodes,Integer status) {
        for(Node node :nodes){
            node.setStatus(status);
        }
        nodeDao.save(nodes);
    }

    public List<Node> findListLimit(Node node, int maxSize) {
        Pageable pageable =
                new PageRequest(0, maxSize);
        Example<Node> example = Example.of(node);
        Page<Node> page = nodeDao.findAll(example,pageable);
        return page.getContent();
    }
}

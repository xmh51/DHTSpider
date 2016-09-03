package me.hncn.dhtspider.model.mongo.node;

import me.hncn.dhtspider.util.ShaUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/** DHT Node
 * Created by xmh on 2016/6/4.
 */
@Document(collection = "node")
public class Node implements Comparable<Node>, Comparator<Node>,Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Field("node_id_hex")
    private String nodeIdHex;
    @Field("node_id_org_byte")
    private byte[] nodeIdOrgByte;
    @Field("ip")
    private String ip;
    @Field("port")
    private Integer port;
    @Field("status")
    private Integer status;
    @Field("create_time")
    private Date createTime;
    @Field("update_time")
    private Date updateTime;

    public Node(byte[] idOrgByte, String ip, Integer port) throws UnsupportedEncodingException {
        nodeIdHex = ShaUtil.byteToHexString(idOrgByte);
        this.nodeIdOrgByte = idOrgByte;
        this.ip = ip;
        this.port = port;
    }

    public Node()  {
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNodeIdHex() {
        return nodeIdHex;
    }

    public void setNodeIdHex(String nodeIdHex) {
        this.nodeIdHex = nodeIdHex;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public byte[] getNodeIdOrgByte() {
        return nodeIdOrgByte;
    }

    public void setNodeIdOrgByte(byte[] nodeIdOrgByte) {
        this.nodeIdOrgByte = nodeIdOrgByte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeIdHex=" + nodeIdHex +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }


    @Override
    public int compare(Node o1, Node o2) {
        if (o1 == o2)
        {
            return 0;
        }
        else if (o1 != null && o2 != null)
        {
           return o1.compareTo(o2);
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return( this.nodeIdHex+ip+port).equals(node.getNodeIdHex()+node.getIp()+node.getPort());

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodeIdOrgByte);
    }

    @Override
    public int compareTo(Node anotherO) {
        int len1 = nodeIdOrgByte.length;
        int len2 = anotherO.getNodeIdOrgByte().length;
        int lim = Math.min(len1, len2);
        byte[] v1 = nodeIdOrgByte;
        byte[] v2 =  anotherO.getNodeIdOrgByte();

        int k = 0;
        while (k < lim) {
            byte c1 = v1[k];
            byte c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
    }


}

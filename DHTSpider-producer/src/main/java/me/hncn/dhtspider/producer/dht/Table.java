package me.hncn.dhtspider.producer.dht;


import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.producer.handle.NodeHandle;
import me.hncn.dhtspider.service.Node.NodeService;
import me.hncn.dhtspider.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by XMH on 2016/6/15.
 */
@Component("table")
public class Table implements Serializable {
    private long addSize=0;
    private static final long serialVersionUID = 1L;
    private TreeSet<Node> table = new TreeSet<>();
    public final int maxSize = 200000;
    private Set<Node> s = Collections.synchronizedSortedSet(table);
    private NodeHandle nodeHandle;
    private NodeService nodeService;
    private BlockingQueue<Node> waitAdd = new ArrayBlockingQueue<>(50000);
    private static Logger logger = LoggerFactory.getLogger(Table.class);

    public Table() {
        this.nodeHandle = SpringContextHolder.getBean(NodeHandle.class);
        this.nodeService = SpringContextHolder.getBean(NodeService.class);
        refresh();
        init();
    }

    public void refresh(){
        s.clear();
        Node node = new Node();
        node.setStatus(1);
        List<Node> nodes = nodeService.findListLimit(node, maxSize);
        s.addAll(nodes);
    }

    private void init() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                logger.info("本轮 共处理 {} node ,update {} add {} 待处理  {} 个 node to table  ", addSize,updateNodeSize, addNodeSize,waitAdd.size());
                logger.info("table total node size {}",s.size());
                addSize = 0;
                addNodeSize=0;
                updateNodeSize=0;
            }
        }, 1, 1000 * 60 * 5);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 2, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 3, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 4, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 5, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 6, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 7, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 8, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 9, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 10, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 11, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 12, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 13, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 14, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 15, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 16, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 17, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 18, 30);
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    while (waitAdd.size() > 0) {
                        Node node = waitAdd.poll();
                        if (node != null) {
                            add(node);
                        }
                    }
                } catch (Exception e) {
                    logger.error("add node error {}", e.getMessage());
                }
            }
        }, 19, 30);

    }

    public void addToQueue(Node node) {
        try {
            waitAdd.put(node);

        } catch (InterruptedException e) {
            logger.error("addToQueue node error {}", e.getMessage());
        }
    }
    private int updateNodeSize= 0;
    private int addNodeSize = 0;
    private void add(Node node) {
        addSize++;
        Node oldByNodeId = getNodeByNodeId(node.getNodeIdOrgByte());

        if (oldByNodeId != null) {
            logger.debug("Update Node IP OR PORT old {} new {}", oldByNodeId, node);
            oldByNodeId.setNodeIdHex(node.getNodeIdHex());
            node = oldByNodeId;
        }

        if (s.size() < maxSize && node.getId()==null) {
            //添加节点
            logger.debug("Add Node " + node.toString());
            node.setCreateTime(new Date());
            s.add(node);
            addNodeSize++;
        }else if(node.getId()!=null){
            //更新节点
            logger.debug("update node {}",node);
            node.setUpdateTime(new Date());
            updateNodeSize++;
        }

        node.setStatus(1);
        nodeHandle.addToQueue(node);

    }

    public void addAll(List<Node> nodes) {
        for (Node node : nodes) {
            addToQueue(node);
        }
    }

    public void remove(Node node) {
        s.remove(node);
    }

    public void removeAll(List<Node> nodes) {
        s.removeAll(nodes);
    }

    public Node[] getAll() {
        Node[] nodes = new Node[0];
        return s.toArray(nodes);
    }

    public int size() {
        return s.size();
    }

    public byte[] getNodesByNodeId(byte[] nodeId) {
        Node node = null;
        try {
            node = new Node(nodeId, "", -11);
            List<Node> list = new ArrayList<>();
            Node target = table.floor(node);
            if (target != null && node.getNodeIdHex().equals(target.getNodeIdHex())) {
                list.add(node); //如果目标在表中存在，则添加目标。
            }
            Node lower = node;
            Node higher = node;
            while (list.size() < 8 && (higher != null || lower != null)) { // 添加 最接近目标node的K(8)个最接近的nodes的联系信息，如果没有八个则终止
                if (lower != null) {
                    lower = table.lower(lower);
                }
                if (higher != null) {
                    higher = table.higher(higher);
                }
                if (lower != null) {
                    list.add(lower);
                }
                if (higher != null) {
                    list.add(higher);
                }
            }

            byte[] result = new byte[list.size() * 26];
            for (int i = 0; i < list.size(); i++) {
                Node no = list.get(i);
                byte[] n = NodeService.getCompactnodeInfobytes(no);
                System.arraycopy(n, 0, result, i * 26, 26);
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从 table 中 获取  不是从数据库中查询
     *
     * @param nodeId
     * @return Node
     */
    public Node getNodeByNodeId(byte[] nodeId) {
        Node node = null;
        try {
            node = new Node(nodeId, "", -11);
            Node target = table.floor(node);
            if (target != null && node.getNodeIdHex().equals(target.getNodeIdHex())) {
                return target;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从 table 中 获取  不是从数据库中查询
     *
     * @param ip
     * @param port
     * @return Node
     */
    private Node getNodeByIpAndPort(String ip, Integer port) {
        Node node = new Node();
        node.setIp(ip);
        node.setPort(port);
        List<Node> nodes = nodeService.findList(node);
        if (nodes.size() > 0) {
            return getNodeByNodeId(nodes.get(0).getNodeIdOrgByte());
        }
        return null;
    }

}

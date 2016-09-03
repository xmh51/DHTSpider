package me.hncn.dhtspider.producer.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.hncn.dhtspider.model.mongo.node.Node;
import me.hncn.dhtspider.spring.SpringContextHolder;
import me.hncn.dhtspider.util.ShaUtil;
import me.hncn.util.net.ScanGetPort;
import net.jcip.annotations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.buffer.ByteBuf;

import javax.swing.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

/**
 * 注意 此此类为多线程，请务必注意线程安全！ 继承 抽象类 请 务必 注意 线程安全！
 * Created by XMH on 2016/6/13.
 */
@NotThreadSafe
public abstract class UdpClientOrServer extends SimpleChannelInboundHandler<DatagramPacket> {
    private Logger logger = LoggerFactory.getLogger(UdpClientOrServer.class);
    private Channel channel;
    private EventLoopGroup group;

    public UdpClientOrServer(String localIp, Integer bindPort) throws IOException {
        InetSocketAddress inetSocketAddress = null;
        if (localIp != null && bindPort != null) {
            inetSocketAddress = new InetSocketAddress(localIp, bindPort);
        } else if (bindPort != null) {
            inetSocketAddress = new InetSocketAddress(bindPort);
        } else {
            inetSocketAddress = new InetSocketAddress(ScanGetPort.getPot(10000));
        }
        group = SpringContextHolder.getBean(NioEventLoopGroup.class);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(this);
            channel = b.bind(inetSocketAddress).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public UdpClientOrServer(Integer bindPort) throws IOException {
        this(null, bindPort);
    }


    public UdpClientOrServer() throws IOException {
        this(null);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        InetSocketAddress isa = packet.sender();
        ByteBuf byteBuf = packet.content();
        int size = byteBuf.readableBytes();
        byte[] bytes = new byte[size];
        byteBuf.getBytes(0,bytes);
        handleReceive(isa, bytes);


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.
    }
    /**
     * @param bytes 发送字符串
     * @param host  远程 host
     * @param port  远程 端口
     */
    protected void sendMsgByUdp(byte[] bytes, String host, int port) {
       // logger.info("发送 Udp {} {} {}", ShaUtil.byteToHexString(bytes), host, port);
        InetSocketAddress isa = new InetSocketAddress(host, port);
        DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer(bytes), isa);

        channel.writeAndFlush(datagramPacket);

    }


    /**
     *
     *
     * @param address 远程 udp 地址
     * @param receive 收到的数据
     */
    public abstract void handleReceive(InetSocketAddress address, byte[] receive) throws UnsupportedEncodingException;
}

package com.cqut.czb.bn.util.minaConlect;


import com.cqut.czb.bn.util.mina.Message;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class ConlectManager {
    private static final String ip = "127.0.0.1";
    private static final int port = 9999;
    private NioSocketConnector connector;
    private InetSocketAddress address;
    private String name;
    private IoSession session;

    public ConlectManager(String name){
        this.name = name;

        address = new InetSocketAddress(ip ,port);
        connector = new NioSocketConnector();

        //设置缓冲区大小
        connector.getSessionConfig().setReceiveBufferSize(2048);

        //添加消息过滤器
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        //设置处理器
        connector.setHandler(new ConnectHandler());

        ConnectFuture connectFuture = connector.connect(address);
        connectFuture.awaitUninterruptibly();
        this.session = connectFuture.getSession();
    }

    public void send(String toUser, String message){
        Message msg = new Message();
        msg.setType(0);
        msg.setMessage(message);
        msg.setUser(this.name);
        msg.setToUser(toUser);

        this.session.write(msg);
    }

    public void login(){
        Message msg = new Message();
        msg.setType(1);
        msg.setUser(this.name);
        msg.setMessage(" Login");

        this.session.write(msg);
    }

    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }

    public NioSocketConnector getConnector() {
        return connector;
    }

    public void setConnector(NioSocketConnector connector) {
        this.connector = connector;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }
}

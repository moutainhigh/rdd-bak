package com.cqut.czb.bn.util.mina;



import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class MinaService {

    static IoAcceptor acceptor = new NioSocketAcceptor();

    public static Map<String, IoSession> link = new HashMap<String, IoSession>();

    public static void init(){
        //添加消息过滤器
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        //添加自己继承IoHandlerAdapter实现的消息处理器
        acceptor.setHandler(new ServerHandler());

        //设置缓冲区配置
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        //开始监听消息
        try{
            acceptor.bind(new InetSocketAddress(9999));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

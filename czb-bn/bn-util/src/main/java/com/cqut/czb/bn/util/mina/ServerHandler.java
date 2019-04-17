package com.cqut.czb.bn.util.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter{
    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        super.sessionCreated(ioSession);
    }
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    //消息接收处理
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        Message msg = (Message)message;

        if(!MinaService.link.containsKey(msg.getUser())){
            MinaService.link.put(msg.getUser(), session);
        }

        //判断是否为普通消息
        if(msg.getType() == 0){
            MinaService.link.get(msg.getToUser()).write(msg);
        }

        System.err.println("Message in the Server:" + msg.toString());
    }

    /**
     * 消息发送处理
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }
}

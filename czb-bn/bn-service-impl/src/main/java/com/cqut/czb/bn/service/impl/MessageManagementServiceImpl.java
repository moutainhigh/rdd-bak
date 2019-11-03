package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.MsgModelMapper;
import com.cqut.czb.bn.dao.mapper.MsgModelMapperExtra;
import com.cqut.czb.bn.entity.dto.MessageManagement.MessageListDTO;
import com.cqut.czb.bn.entity.dto.appMessageManage.MsgRecordDTO;
import com.cqut.czb.bn.entity.entity.MsgModel;
import com.cqut.czb.bn.entity.entity.MsgRecord;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.MessageManagementService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-17 11:32
 */
@Service
public class MessageManagementServiceImpl implements MessageManagementService {

    @Autowired
    MsgModelMapperExtra msgModelMapperExtra;

    @Autowired
    MsgModelMapper msgModelMapper;

    private static Random random = new Random();

    @Override
    public PageInfo getMessageList(MessageListDTO messageListDTO) {
        PageHelper.startPage(messageListDTO.getCurrentPage(),messageListDTO.getPageSize());
        return new PageInfo<>(msgModelMapperExtra.getMessageModelList(messageListDTO));
    }

    @Override
    public Boolean deleteMsgModelById(String msgModelId) {
        return msgModelMapper.deleteByPrimaryKey(msgModelId) > 0;
    }

    @Override
    public Boolean createMsgModel(MsgModel msgModel) {
        try{
            //默认为未发送状态
            if(msgModel.getMsgContent() == null || msgModel.getMsgTitle() == null || msgModel.getMsgType() == null || msgModel.getReceiverType() == null || msgModel.getAltert() == null){
                return false;
            }
            msgModel.setIsSend(0);
            msgModel.setMsgModelId(StringUtil.createId());
            msgModel.setCreateAt(new Date());
            msgModel.setUpdateAt(new Date());
            return msgModelMapper.insert(msgModel) > 0;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean sendMessage(String msgModelId) {
        try{
            //创建多个ID，使用纳秒级的时间戳
            MsgModel msgModel = msgModelMapper.selectByPrimaryKey(msgModelId);
            //若信息已发送则不发送消息
            if(1 == msgModel.getIsSend()){
                return false;
            }
            String idPrex = String.valueOf(System.currentTimeMillis());
            List<MsgRecord> msgRecordList = msgModelMapperExtra.getMessageRecordList(msgModelId,msgModel.getReceiverType());
            for(int i = 0; i < msgRecordList.size(); i++){
                msgRecordList.get(i).setMsgModelId(idPrex + i);
            }
            msgModel.setIsSend(1);
            msgModelMapper.updateByPrimaryKey(msgModel);
            return msgModelMapperExtra.insertMessages(msgRecordList) > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static String createMillisTimestamp() {
        return String.valueOf(System.nanoTime());
    }

    public static String createId() {
        return createMillisTimestamp() + "" + random.nextInt(10) + "" + random.nextInt(10);
    }


    @Override
    public boolean sendMessageToOne(Map<String, String> maps, String userId) {
        String msgModelId = maps.get("msgModelId");
        String receiverId = maps.get("receiverId");
        if(msgModelId == null || userId == null || receiverId == null) {
            return false;
        }
        if(sendMsg(msgModelId, maps, userId, receiverId))
            return true;
        else
            return false;
    }

    /**
     *
     * @param msgModelId
     * @param content
     * @return
     */
    public boolean sendMsg(String msgModelId, Map<String, String> content, String announcerId, String receiverId) {
        MsgModel msgModel = msgModelMapper.selectByPrimaryKey(msgModelId);
        for(Map.Entry<String, String> entry : content.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            mapKey = "${" + mapKey + "}";
            if(msgModel.getMsgContent().contains(mapKey)) {
                msgModel.setMsgContent(msgModel.getMsgContent().replace(mapKey, mapValue));
            }
        }

        List<MsgRecord> msgRecordList = new ArrayList<>();
        MsgRecord record = new MsgRecord();
        record.setMsgRecordId(createId());
        record.setMsgModelId(msgModelId);
        record.setAlert(msgModel.getAltert());
        record.setMsgState(0);
        record.setMsgAnnouncerId(announcerId);
        record.setMsgReceiverId(receiverId);
        record.setContent(msgModel.getMsgContent());
        msgRecordList.add(record);
        return msgModelMapperExtra.insertRecord(msgRecordList) > 0;
    }
}
package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.MsgModelMapper;
import com.cqut.czb.bn.dao.mapper.MsgModelMapperExtra;
import com.cqut.czb.bn.entity.dto.MessageManagement.MessageListDTO;
import com.cqut.czb.bn.entity.entity.MsgModel;
import com.cqut.czb.bn.service.MessageManagementService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            msgModel.setIsSend(0);
            msgModel.setMsgModelId(StringUtil.createId());
            return msgModelMapper.insert(msgModel) > 0;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean sendMessage(String msgModelId) {
        MsgModel msgModel = msgModelMapper.selectByPrimaryKey(msgModelId);
        return null;
    }
}
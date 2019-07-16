package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.MsgRecordMapperExtra;
import com.cqut.czb.bn.entity.dto.appMessageManage.ModifyInfoDTO;
import com.cqut.czb.bn.entity.dto.appMessageManage.MsgNumDTO;
import com.cqut.czb.bn.entity.dto.appMessageManage.MsgRecordDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.AppMessageManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.List;
@Service
public class AppMessageManageServiceImpl implements AppMessageManageService {
    @Autowired
    public MsgRecordMapperExtra msgRecordMapperExtra;

    @Override
    public List<MsgRecordDTO> getMessages(User user) {
        if(user==null) {
            return null;
        }
        return  msgRecordMapperExtra.selectMsgRecords(user.getUserId());
    }

    @Override
    public int getMsgNum(User user) {
        return msgRecordMapperExtra.selectMsgNum(user.getUserId());
    }

    @Override
    public MsgRecordDTO getOnePopMessages(User user) {
        if(user==null){
            return null;
        }
        MsgRecordDTO msgRecordDTO=msgRecordMapperExtra.getOnePopMessages(user.getUserId());
        return msgRecordDTO;
    }

    @Override
    public boolean modifyMessage(User user, ModifyInfoDTO modifyInfoDTO) {
        modifyInfoDTO.setMsgReceiverId(user.getUserId());
        return msgRecordMapperExtra.modifyMessage(modifyInfoDTO)>0;
    }
}

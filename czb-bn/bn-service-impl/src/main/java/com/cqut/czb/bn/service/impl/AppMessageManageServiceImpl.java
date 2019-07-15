package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.MsgRecordMapperExtra;
import com.cqut.czb.bn.entity.dto.appMessageManage.ModifyInfoDTO;
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
        if(user==null){
            return null;
        }
        List<MsgRecordDTO> msgRecordDTOS=msgRecordMapperExtra.selectMsgRecords(user.getUserId());
        if(msgRecordDTOS==null)
        {
            return  null;
        }
        for(int i=0;i<msgRecordDTOS.size();i++){
            msgRecordDTOS.get(i).setCreateTime(DateFormat.getDateInstance().format(msgRecordDTOS.get(i).getCreateAt()));
        }
        return msgRecordDTOS;
    }

    @Override
    public MsgRecordDTO getOnePopMessages(User user) {
        if(user==null){
            return null;
        }
        MsgRecordDTO msgRecordDTO=msgRecordMapperExtra.getOnePopMessages(user.getUserId());
        if (msgRecordDTO==null){
            return null;
        }
        msgRecordDTO.setCreateTime(DateFormat.getDateInstance().format(msgRecordDTO.getCreateAt()));
        return msgRecordDTO;
    }

    @Override
    public boolean modifyMessage(User user, ModifyInfoDTO modifyInfoDTO) {
        modifyInfoDTO.setMsgReceiverId(user.getUserId());
        return msgRecordMapperExtra.modifyMessage(modifyInfoDTO)>0;
    }
}

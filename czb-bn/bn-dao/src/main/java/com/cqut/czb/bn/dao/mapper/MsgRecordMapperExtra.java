package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appMessageManage.ModifyInfoDTO;
import com.cqut.czb.bn.entity.dto.appMessageManage.MsgRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgRecordMapperExtra {

    List<MsgRecordDTO> selectMsgRecords(@Param("msgReceiverId") String msgReceiverId);

    MsgRecordDTO getOnePopMessages(@Param("msgReceiverId") String msgReceiverId);

    int modifyMessage(ModifyInfoDTO modifyInfoDTO);
}
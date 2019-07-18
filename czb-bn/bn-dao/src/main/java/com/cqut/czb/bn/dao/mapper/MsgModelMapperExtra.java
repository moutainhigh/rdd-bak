package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.MessageManagement.MessageListDTO;
import com.cqut.czb.bn.entity.entity.MsgRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-17 15:37
 */
public interface MsgModelMapperExtra {
    public List<MessageListDTO> getMessageModelList(@Param("messageListDTO") MessageListDTO messageListDTO);

    public List<MsgRecord> getMessageRecordList(String msgModelId);
}
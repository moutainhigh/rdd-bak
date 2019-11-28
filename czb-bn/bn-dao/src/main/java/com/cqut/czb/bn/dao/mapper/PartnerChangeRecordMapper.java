package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PartnerChangeRecord;

public interface PartnerChangeRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PartnerChangeRecord record);

    int insertSelective(PartnerChangeRecord record);

    PartnerChangeRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PartnerChangeRecord record);

    int updateByPrimaryKey(PartnerChangeRecord record);
}
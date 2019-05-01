package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.VerificationCode;

public interface VerificationCodeMapper {
    int deleteByPrimaryKey(String verificationCodeId);

    int insert(VerificationCode record);

    int insertSelective(VerificationCode record);

    VerificationCode selectByPrimaryKey(String verificationCodeId);

    int updateByPrimaryKeySelective(VerificationCode record);

    int updateByPrimaryKey(VerificationCode record);
}
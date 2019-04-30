package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.ExpressDTO;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExpressMapper {
    List<ExpressDTO> selectExpress(@Param("expressNumber")String expressNumber,@Param("expressCompany")String expressCompany, @Param("consignee")String consignee);

    Boolean addExpress(@Param("express") ExpressDTO expressDTO);
}

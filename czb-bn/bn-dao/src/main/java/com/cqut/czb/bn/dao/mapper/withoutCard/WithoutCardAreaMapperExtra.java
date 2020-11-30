package com.cqut.czb.bn.dao.mapper.withoutCard;


import com.cqut.czb.bn.entity.dto.withoutCard.WithoutCardAreaConfigDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithoutCardAreaMapperExtra {

    List<WithoutCardAreaConfigDto> listWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto);

    int insertWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto);

    int removetWithoutCardArea(@Param("petrolConfigId")String petrolConfigId);

    int updatetWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto);

    WithoutCardAreaConfigDto gettWithoutCardAreaById(@Param("petrolConfigId")String petrolConfigId);
}

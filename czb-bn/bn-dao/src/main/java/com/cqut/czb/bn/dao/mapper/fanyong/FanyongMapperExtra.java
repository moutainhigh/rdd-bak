package com.cqut.czb.bn.dao.mapper.fanyong;

import com.cqut.czb.bn.entity.dto.fanyong.FanyongLogDto;

import java.util.List;

public interface FanyongMapperExtra {
    List<FanyongLogDto> getLogData(FanyongLogDto fanyongLogDto);

    Double getTotal(FanyongLogDto fanyongLogDto);

    boolean isContainFanyongLog(String orgId);
}

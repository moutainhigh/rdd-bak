package com.cqut.czb.bn.dao.mapper.withDrawals;

import com.cqut.czb.bn.entity.dto.fanyong.FanyongLogDto;

import java.util.List;

public interface WithDrawalsMapperExtra {
    List<FanyongLogDto> getRecode(FanyongLogDto fanyongLogDto);

    Double getTotal(FanyongLogDto fanyongLogDto);
}

package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.shop.FileFunctionDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.FileFunction;

import java.util.List;

public interface FileFunctionMapperExtra {
    List<FileFunctionDTO> selectFile(ShopDTO shopDTO);

    List<FileFunction> selectSrc(ShopDTO shopDTO);

    int deleteByIds(List<FileFunctionDTO> list);

    int insertFile(FileFunctionDTO fileFunctionDTO);
}

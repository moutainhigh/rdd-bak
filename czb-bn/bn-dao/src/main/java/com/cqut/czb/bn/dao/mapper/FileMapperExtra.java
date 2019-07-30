package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.shop.FileFunctionDTO;
import com.cqut.czb.bn.entity.entity.File;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FileMapperExtra {
    int deleteByPrimaryKey(String fileId);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(String fileId);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);

    int deleteByShop(List<FileFunctionDTO> list);
}

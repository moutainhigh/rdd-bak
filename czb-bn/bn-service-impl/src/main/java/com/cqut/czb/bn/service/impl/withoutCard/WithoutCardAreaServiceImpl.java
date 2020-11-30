package com.cqut.czb.bn.service.impl.withoutCard;

import com.cqut.czb.bn.dao.mapper.withoutCard.WithoutCardAreaMapperExtra;
import com.cqut.czb.bn.entity.dto.withoutCard.WithoutCardAreaConfigDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.withoutCard.WithoutCardAreaService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithoutCardAreaServiceImpl implements WithoutCardAreaService {

    @Autowired
    WithoutCardAreaMapperExtra withoutCardAreaMapperExtra;


    @Override
    public PageInfo<WithoutCardAreaConfigDto> listWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto) {
        PageHelper.startPage(withoutCardAreaConfigDto.getCurrentPage(),withoutCardAreaConfigDto.getPageSize());
        return new PageInfo<>(withoutCardAreaMapperExtra.listWithoutCardArea(withoutCardAreaConfigDto));
    }

    @Override
    public JSONResult insertWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto) {
        withoutCardAreaConfigDto.setPetrolConfigId(StringUtil.createId());
        return withoutCardAreaMapperExtra.insertWithoutCardArea(withoutCardAreaConfigDto) > 0 ? new JSONResult(200,"新增成功") : new JSONResult(500,"新增失败");

    }

    @Override
    public JSONResult removetWithoutCardArea(String petrolConfigId) {
        return withoutCardAreaMapperExtra.removetWithoutCardArea(petrolConfigId) > 0 ? new JSONResult(200,"删除成功") : new JSONResult(500,"删除失败");
    }

    @Override
    public JSONResult updatetWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto) {
        return withoutCardAreaMapperExtra.updatetWithoutCardArea(withoutCardAreaConfigDto) > 0 ? new JSONResult(200,"更新成功") : new JSONResult(500,"更新失败");
    }

    @Override
    public WithoutCardAreaConfigDto gettWithoutCardAreaById(String petrolConfigId) {
        return withoutCardAreaMapperExtra.gettWithoutCardAreaById(petrolConfigId);
    }
}

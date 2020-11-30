package com.cqut.czb.bn.service.withoutCard;



import com.cqut.czb.bn.entity.dto.withoutCard.WithoutCardAreaConfigDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/**
 * 作者： 陈爽
 * 模块： 无卡加油区域管理
 * 创建时间： 2020/11/17
 */
@Component
public interface WithoutCardAreaService {


    PageInfo<WithoutCardAreaConfigDto> listWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto);

    JSONResult insertWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto);

    JSONResult removetWithoutCardArea(String petrolConfigId);

    JSONResult updatetWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto);

    WithoutCardAreaConfigDto gettWithoutCardAreaById(String petrolConfigId);
}

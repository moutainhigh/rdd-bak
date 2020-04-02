package com.cqut.czb.bn.service.impl.AutomaticRechargeServiceImpl;

import com.cqut.czb.bn.dao.mapper.AutomaticRechargeMapperExtra;
import com.cqut.czb.bn.dao.mapper.autoRecharge.AutoRechargeRecordMapper;
import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.entity.autoRecharge.AutoRechargeRecord;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.automaticRechargeService.AutomaticRechargeService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("AutomaticRechargeService")
public class AutomaticRechargeServiceImpl implements AutomaticRechargeService {

    @Autowired
    AutomaticRechargeMapperExtra automaticRechargeMapperExtra;

    @Autowired
    AutoRechargeRecordMapper autoRechargeRecordMapper;

    @Override
    public JSONResult getAutoList(AutomaticRechargeDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        List<AutomaticRechargeDTO> withdrawList = automaticRechargeMapperExtra.getAutoList(pageDTO);
        PageInfo<AutomaticRechargeDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult deleteRecorder(String id) {
        int deleNum = automaticRechargeMapperExtra.deleteRecorder(id);
        if (deleNum != 0) {
            return new JSONResult("删除成功", 200);
        }
        return new JSONResult("删除失败", 400);
    }

    @Override
    public JSONResult showRecorder(String id) {
        AutomaticRechargeDTO automaticRechargeDTO = automaticRechargeMapperExtra.showRecorder(id);
        return new JSONResult("列表数据查询成功", 200, automaticRechargeDTO);
    }

    @Override
    public Boolean insertRecord(AutoRechargeRecord autoRechargeRecord) {
        autoRechargeRecord.setAutoRechargeRecordId(StringUtil.createId());
        autoRechargeRecord.setRechargeTime(new Date());
        autoRechargeRecord.setCreateAt(new Date());
        autoRechargeRecord.setUpdateAt(new Date());
        return autoRechargeRecordMapper.insertSelective(autoRechargeRecord) > 0;
    }

    @Override
    public JSONResult editRecorder(AutomaticRechargeDTO automaticRechargeDTO) {
        int updata = automaticRechargeMapperExtra.editRecorder(automaticRechargeDTO);
        if (updata != 0) {
            return new JSONResult("修改成功", 200);
        }
        return new JSONResult("修改失败", 200);
    }
}

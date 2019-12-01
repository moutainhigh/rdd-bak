package com.cqut.czb.bn.service.impl.PetrolPriceReport;

import com.cqut.czb.bn.dao.mapper.PetrolPriceReportMapper;
import com.cqut.czb.bn.dao.mapper.PetrolPriceReportMapperExtra;
import com.cqut.czb.bn.entity.dto.PetrolPriceReportInputDTO;
import com.cqut.czb.bn.entity.entity.PetrolPriceReport;
import com.cqut.czb.bn.service.petrolPriceReport.PetrolPriceReportService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetrolPriceReportServiceImpl implements PetrolPriceReportService {

    @Autowired
    PetrolPriceReportMapperExtra petrolPriceReportMapperExtra;

    @Autowired
    PetrolPriceReportMapper petrolPriceReportMapper;

    @Override
    public PageInfo<PetrolPriceReport> selectReportList(PetrolPriceReportInputDTO petrolPriceReportInputDTO) {
        PageHelper.startPage(petrolPriceReportInputDTO.getCurrentPage(), petrolPriceReportInputDTO.getPageSize());
        List<PetrolPriceReport> list= petrolPriceReportMapperExtra.selectPetrolPriceReport(petrolPriceReportInputDTO);
        return new PageInfo<>(list);
    }

    @Override
    public boolean deletePetrolReport(String reportId) {
        return petrolPriceReportMapper.deleteByPrimaryKey(reportId)>0;
    }

    @Override
    public boolean editPetrolReport(PetrolPriceReport record) {
        return petrolPriceReportMapper.updateByPrimaryKeySelective(record)>0;
    }

    @Override
    public boolean addPetrolReport(PetrolPriceReport record) {
        record.setPetrolPriceReportId(StringUtil.createId());
        return petrolPriceReportMapper.insertSelective(record)>0;
    }

}

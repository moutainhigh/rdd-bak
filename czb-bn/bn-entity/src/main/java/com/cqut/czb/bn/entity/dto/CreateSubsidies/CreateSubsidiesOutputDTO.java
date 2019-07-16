package com.cqut.czb.bn.entity.dto.CreateSubsidies;

import com.github.pagehelper.PageInfo;

public class CreateSubsidiesOutputDTO {



    private PageInfo<CreateSubsidiesQueryDTO> createSubsidiesQueryDTOList;

    private Double totalSubsidies;

    public PageInfo<CreateSubsidiesQueryDTO> getCreateSubsidiesQueryDTOList() {
        return createSubsidiesQueryDTOList;
    }

    public void setCreateSubsidiesQueryDTOList(PageInfo<CreateSubsidiesQueryDTO> createSubsidiesQueryDTOList) {
        this.createSubsidiesQueryDTOList = createSubsidiesQueryDTOList;
    }

    public Double getTotalSubsidies() {
        return totalSubsidies;
    }

    public void setTotalSubsidies(Double totalSubsidies) {
        this.totalSubsidies = totalSubsidies;
    }
}

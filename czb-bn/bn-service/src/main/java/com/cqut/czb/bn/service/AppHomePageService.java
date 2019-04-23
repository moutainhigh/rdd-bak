package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;
import com.cqut.czb.bn.entity.entity.ServicePlan;

import java.util.List;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/20
 * 作用：广告表管理
 */
public interface AppHomePageService {

    /**
     * 获取广告内容表
     * @return
     */
    List<Announcement> selectAnnouncement();

    /**
     * 获取油卡销售地域配置表
     * @return
     */
    List<PetrolSaleConfig> selectPetrolSaleConfig();

    /**
     * 获取服务套餐表
     * @return
     */
    List<ServicePlan> selectServicePlan();

    /**
     * 获取未售出的油卡
     * @return
     */
    List<PetrolZoneDTO> selectPetrolZone();

}

package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.petrolPriceReportDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PetrolInfoDTO;
import com.cqut.czb.bn.entity.entity.*;

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
    List<appAnnouncementDTO> selectAnnouncement();

    /**
     * 油价播报
     * @param area
     * @return
     */
    List<petrolPriceReportDTO> selectPetrolPriceReport(String area);

    /**
     * 获取服务套餐表
     * @return
     */
    List<ServicePlan> selectServicePlan();

    /**
     * 获取油卡专区
     * @param area
     * @return
     */
    List<PetrolZoneDTO> selectPetrolZone(String area);

    /**
     * 获取未售出的油卡
     * @return
     */
    boolean selectAllPetrol();

    /**
     * 获取首页的所有路由
     * @param appRouterDTO
     * @return
     */
    List<AppRouterDTO> selectHomePageRouters(AppRouterDTO appRouterDTO);

    List<PetrolInfoDTO> selectPetrolInfoDTO();

}

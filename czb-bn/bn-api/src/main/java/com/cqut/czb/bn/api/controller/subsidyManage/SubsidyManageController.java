package com.cqut.czb.bn.api.controller.subsidyManage;

import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesQueryDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.subsidyManage.SubsidySearchDTO;
import com.cqut.czb.bn.entity.entity.subsidyManage.Subsidy;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyMission;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyMissionUser;
import com.cqut.czb.bn.entity.entity.subsidyManage.UserIds;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.subsidyManage.SubsidyManageService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * auth: 谭深化
 * module：后台补贴模块
 * time：2019-07-11
 */

@CrossOrigin
@RestController
@RequestMapping("/api/subsidyManage")
public class SubsidyManageController {
    @Autowired
    private SubsidyManageService subsidyManageService;

    /**
     * 获取表格数据
     * @param searchDTO
     * @return
     */
    @GetMapping("/getSubsidyManageData")
    public JSONResult getTableData(SubsidySearchDTO searchDTO, PageDTO pageDTO) {
        PageInfo<Subsidy> subsidyList = subsidyManageService.getSubsidyData(searchDTO, pageDTO);

        return new JSONResult(subsidyList);
    }


    /**
     * 补贴管理表格数据获取
     * @param searchDTO
     * @param pageDTO
     * @return
     */
    @GetMapping("/getSubsidyMissionData")
    public JSONResult getSubsidyMissionData(SubsidySearchDTO searchDTO, PageDTO pageDTO){
        PageInfo<SubsidyMission> subsidyMissionList = subsidyManageService.getSubsidyMissionData(searchDTO, pageDTO);

        return new JSONResult(subsidyMissionList);
    }

    /**
     * 删除补贴任务
     * @param
     * @return
     */
    @PostMapping("/deleteSubsidyMission")
    public JSONResult deleteSubsidyMission(@RequestBody SubsidyMissionUser input) {
        boolean success = subsidyManageService.deleteSubsidyMission(input.getMissionId());
        if (success) {
            return new JSONResult("删除成功",200);
        } else {
            return new JSONResult("删除失败", 500);
        }
    }

    /**
     * 发放补贴
     * @param input
     * @return
     */
    @PostMapping("/sendSubsidy")
    public JSONResult sendSubsidy(@RequestBody SubsidyMissionUser input) {
        return subsidyManageService.sendSubsidy(input.getMissionId());
    }

    /**
     * 创建补贴任务
     * @param input
     * @return
     */
    @GetMapping("/createSubsidyMission")
    public JSONResult createSubsidyMission(CreateSubsidiesQueryDTO createDto, UserIds input) {
        return subsidyManageService.createSubsidyMission(createDto, input);
    }

}

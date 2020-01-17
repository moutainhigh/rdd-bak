package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOutputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.WCPCommodityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-25 14:40
 */
@RestController
@RequestMapping("/api/WCPCommodityInfo")
public class WCPCommodityInfoController {

    @Autowired
    WCPCommodityInfoService wcpCommodityInfoService;

    @GetMapping("/getCommodityList")
    public JSONResult getCommodityList(WCPCommodityInputDTO wcpCommodityInputDTO){
        return new JSONResult(wcpCommodityInfoService.getCommodity(wcpCommodityInputDTO));
    }
    @GetMapping("/getCommodityTitleList")
    public JSONResult getCommodityTitleList(WCPCommodityOutputDTO wcpCommodityOutputDTO){
        return wcpCommodityInfoService.getCommodityTitle(wcpCommodityOutputDTO);
    }

    @GetMapping("/getOneCommodity")
    public JSONResult getOneCommodity(@Valid WCPCommodityInputDTO wcpCommodityInputDTO){
        return new JSONResult(wcpCommodityInfoService.getOneCommodityById(wcpCommodityInputDTO));
    }

    @GetMapping("/getAreas")
    public JSONResult getAreas(){
        return new JSONResult(wcpCommodityInfoService.getAreas());
    }

    @GetMapping("/getClassification")
    public JSONResult getClassification(WCPCommodityOutputDTO wcpCommodityOutputDTO){
        return new JSONResult(wcpCommodityInfoService.getClassification(wcpCommodityOutputDTO));
    }

    @GetMapping("/getContent")
    public JSONResult getContent(){
        return new JSONResult(wcpCommodityInfoService.getContent());
    }
}
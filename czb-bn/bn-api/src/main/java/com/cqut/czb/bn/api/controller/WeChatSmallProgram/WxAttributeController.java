package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Attribute;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/wxAttribute")
public class WxAttributeController {
    @Autowired
    WxAttributeService wxAttributeService;


    @GetMapping("/getAllAttribute")
    public JSONResult getAllAttribute(Attribute attribute, PageDTO pageDTO){
        return new JSONResult(wxAttributeService.getAllAttribute(attribute,pageDTO));
    }

    @PostMapping("/addAttribute")
    public JSONResult addAttribute(@RequestBody Attribute attribute){
        Boolean add = wxAttributeService.addAttribute(attribute);
        if (add){
            return new JSONResult(200,add);
        }else {
            return new JSONResult(500,add);
        }

    }

    @PostMapping("/editAttribute")
    public JSONResult editAttribute(@RequestBody Attribute attribute){
        Boolean edit = wxAttributeService.editAttribute(attribute);
        if (edit){
            return new JSONResult(200,edit);
        }else {
            return new JSONResult(500,edit);
        }

    }

    @PostMapping("/deleteAttribute")
    public JSONResult deleteAttribute(String attributeId){
        Boolean delete = wxAttributeService.deleteAttribute(attributeId);
        if (delete){
            return new JSONResult(200,delete);
        }else {
            return new JSONResult(500,delete);
        }
    }


}

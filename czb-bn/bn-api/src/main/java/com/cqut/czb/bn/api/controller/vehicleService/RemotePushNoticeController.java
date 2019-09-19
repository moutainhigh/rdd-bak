package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.PushDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RemotePushNoticeDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePushNotice;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.RemotePushNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/remotePushNotice")
@RestController
public class RemotePushNoticeController {
    @Autowired
    RemotePushNoticeService service;

    @GetMapping("search")
    public JSONResult search(RemotePushNotice notice, PageDTO pageDTO) {
        return service.search(notice, pageDTO);
    }

    @PostMapping("add")
    public JSONResult add(@RequestBody RemotePushNotice notice) {
        return service.add(notice);
    }

    @PostMapping("edit")
    public JSONResult edit(@RequestBody RemotePushNotice notice) {
        return service.edit(notice);
    }

    @PostMapping("delete")
    public JSONResult delete(@RequestBody RemotePushNoticeDTO notice) {
        return service.delete(notice);
    }

    @GetMapping("getRemotePushNoticeType")
    public JSONResult getRemotePushNoticeType(RemotePushNotice notice) {
        return new JSONResult(service.getRemotePushNoticeType(notice)) ;
    }

    @PostMapping("addPush")
    public JSONResult addPush(@RequestBody PushDTO pushDTO) {
        return new JSONResult(service.addPush(pushDTO));
    }
}

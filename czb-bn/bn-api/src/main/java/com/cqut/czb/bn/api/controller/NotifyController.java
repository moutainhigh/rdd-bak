package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.NotificationInputDTO;
import com.cqut.czb.bn.entity.dto.NotifyInsertInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.INotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/notify")
public class NotifyController {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    INotifyService notifyService;
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public JSONResult insertNotification(NotifyInsertInputDTO notify){
//        Notify notify = new Notify();
//        notify.setMoney(Double.valueOf(money));
//        notify.setNotifyId(System.currentTimeMillis()+"");
//        notify.setPayType("微信");
//        notify.setState(1);
        return new JSONResult(notifyService.insert(notify.getNotify()));
    }

    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public JSONResult getNotificationList(NotificationInputDTO notificationInputDTO){
        return new JSONResult(notifyService.getList(notificationInputDTO));
    }

    @RequestMapping(value ="/getCount",method = RequestMethod.GET)
    public JSONResult getCount(){
        return new JSONResult(notifyService.getCount());
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public JSONResult getNotificationList(Principal principal){
        User user = redisUtils.get(principal.getName());
        return new JSONResult(redisUtils.hasKey(principal.getName()));
    }
}

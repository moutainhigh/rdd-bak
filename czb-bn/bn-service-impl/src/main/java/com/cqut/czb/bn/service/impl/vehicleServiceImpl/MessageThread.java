package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.AppRouterMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushNoticeMapperExtra;
import com.cqut.czb.bn.entity.dto.PushIOSDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RemotePushNoticesDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Component
@ComponentScan
public class MessageThread implements Runnable {
    String noticeId;

    String userId;
    
    Map<String,String> content = new HashMap<>();

    Integer type = 2;

    @Autowired
    CleanRiderMapperExtra cleanRiderMapper;
    @Autowired
    RemotePushMapperExtra remotePushMapperExtra;
    @Autowired
    RemotePushNoticeMapperExtra remotePushNoticeMapperExtra;
    @Autowired
    AppRouterMapper appRouterMapper;

    public CleanRiderMapperExtra getCleanRiderMapper() {
        return cleanRiderMapper;
    }

    public void setCleanRiderMapper(CleanRiderMapperExtra cleanRiderMapper) {
        this.cleanRiderMapper = cleanRiderMapper;
    }

    public RemotePushMapperExtra getRemotePushMapperExtra() {
        return remotePushMapperExtra;
    }

    public void setRemotePushMapperExtra(RemotePushMapperExtra remotePushMapperExtra) {
        this.remotePushMapperExtra = remotePushMapperExtra;
    }

    public RemotePushNoticeMapperExtra getRemotePushNoticeMapperExtra() {
        return remotePushNoticeMapperExtra;
    }

    public void setRemotePushNoticeMapperExtra(RemotePushNoticeMapperExtra remotePushNoticeMapperExtra) {
        this.remotePushNoticeMapperExtra = remotePushNoticeMapperExtra;
    }

    public AppRouterMapper getAppRouterMapper() {
        return appRouterMapper;
    }

    public void setAppRouterMapper(AppRouterMapper appRouterMapper) {
        this.appRouterMapper = appRouterMapper;
    }

    @Override
    public void run() {
        String deviceToken = "";
        if (type==2) {
        RemotePush remotePush = remotePushMapperExtra.selectByUser(userId);
        if (remotePush.getDeviceType() != 2 && remotePush.getDeviceType() != 3) {
            return;
        }
         deviceToken = remotePush.getDeviceToken();
        }
            RemotePushNoticesDTO remotePushNotice = remotePushNoticeMapperExtra.selectById(noticeId);
            if (!content.isEmpty()){
                for (Map.Entry<String, String> exp:content.entrySet()) {
                   if (remotePushNotice!=null) {
                      remotePushNotice.setNoticeContent(remotePushNotice.getNoticeContent().replace("${"+exp.getKey()+"}",exp.getValue()));
                  }else {
                      return ;
                  }
                }
            }
            String  alert  = remotePushNotice.getNoticeContent();//push的内容
            int badge = 1;//图标小红圈的数值
            String sound = "default";//铃音
            List<String> tokens = new ArrayList<String>();
            tokens.add(deviceToken);
            String certificatePath = "iosPush.p12";
//            String certificatePath = "D:\\DDM\\czb-server\\czb-bn\\bn-util\\src\\main\\java\\com\\cqut\\czb\\bn\\util\\certificate\\iosPush.p12";
            String certificatePassword = "renduoduo2019";//此处注意导出的证书密码不能为空因为空密码会报错
            boolean sendCount = true;
            if (type==3){
                sendCount = false;
            }
            try
            {
                PushNotificationPayload payLoad = new PushNotificationPayload();
                PushIOSDTO pushDTO = new PushIOSDTO();
                if (remotePushNotice.getAppRouterId()!=null){
                    AppRouter appRouter = appRouterMapper.selectByPrimaryKey(remotePushNotice.getAppRouterId());
                    if (appRouter!=null && appRouter.getRouterId()!=null) {
                        pushDTO.setIosPath(appRouter.getIosPath());
                        pushDTO.setMenuName(appRouter.getMenuName());
                        pushDTO.setPathType(appRouter.getPathType());
                    }
                }
                pushDTO.setTitle(remotePushNotice.getNoticeContent());
                payLoad.addCustomDictionary("appRouter", JSONObject.fromObject(pushDTO));
                payLoad.addAlert(alert); // 消息内容
                payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
                if (!StringUtils.isBlank(sound))
                {
                    payLoad.addSound(sound);//铃音
                }
                PushNotificationManager pushManager = new PushNotificationManager();
                //true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
                pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, true));
                List<PushedNotification> notifications = new ArrayList<PushedNotification>();
                System.out.println("发送push消息");
                // 发送push消息
                if (sendCount)
                {
                    Device device = new BasicDevice();
                    device.setToken(tokens.get(0));
                    PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                    notifications.add(notification);
                }
                else
                {
                    List<Device> device = new ArrayList<>();
                    RemotePush exp = new RemotePush();
                    exp.setDeviceType(2);
                    List<RemotePush> remotePushes = remotePushMapperExtra.selectByPlatform(exp);
//                    int count = 0; // 每次发送 1000 条，count 记录下
                    for (int i = 0; i < remotePushes.size(); i++ ){
                        if (remotePushes.get(i).getDeviceToken() != null && !"".equals(remotePushes.get(i).getDeviceToken())){
                            device.add(new BasicDevice(remotePushes.get(i).getDeviceToken()));
                        }
//                        count += 1;
//                        if (count / 1000  == 1 || i == remotePushes.size() - 1){
//                            count %= 1000;
//
//                            device.clear();
//                        }
                    }
                    notifications = pushManager.sendNotifications(payLoad, device);
                    int success = 0;
                    int failed = 0;
                    for (PushedNotification pushedNotification: notifications){
                        if (pushedNotification.isSuccessful()) {
                            success += 1;
                        }else{
                            failed += 1;
                        }
                    }
                    System.out.println("iOS 推送，成功（条数）：" + success + "失败（条数）" + failed);
                }
                pushManager.stopConnection();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }
}

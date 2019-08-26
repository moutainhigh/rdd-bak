package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.AppRouterMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushNoticeMapperExtra;
import com.cqut.czb.bn.entity.dto.PushDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePushNotice;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Component
@ComponentScan
public class MessageThread implements Runnable {
    static String noticeId;

    static String userId;

    @Autowired
    CleanRiderMapperExtra cleanRiderMapper;
    @Autowired
    RemotePushMapperExtra remotePushMapperExtra;
    @Autowired
    RemotePushNoticeMapperExtra remotePushNoticeMapperExtra;
    @Autowired
    AppRouterMapper appRouterMapper;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
            RemotePush remotePush = remotePushMapperExtra.selectByUser(userId);
            if (remotePush.getDeviceType()!=2){
                return;
            }
////    "768878996dc4f6fee4b367a24d609a0208088abcce88a4b86259b12a494b0817"
            String deviceToken = remotePush.getDeviceToken();
            RemotePushNotice remotePushNotice = remotePushNoticeMapperExtra.selectById(noticeId);
            String  alert  = remotePushNotice.getNoticeContent();//push的内容
//      String deviceToken = "768878996dc4f6fee4b367a24d609a0208088abcce88a4b86259b12a494b0817";
//      String  alert  ="有骑手接单了";//push的内容
            int badge = 1;//图标小红圈的数值
            String sound = "default";//铃音
            List<String> tokens = new ArrayList<String>();
            tokens.add(deviceToken);
            String certificatePath = "iosPush.p12";
            String certificatePassword = "renduoduo2019";//此处注意导出的证书密码不能为空因为空密码会报错
            boolean sendCount = true;
            try
            {
                PushNotificationPayload payLoad = new PushNotificationPayload();
                AppRouter appRouter = appRouterMapper.selectByPrimaryKey(remotePushNotice.getAppRouterId());
                PushDTO pushDTO = new PushDTO();
                pushDTO.setIosPath(appRouter.getIosPath());
                pushDTO.setMenuName(appRouter.getMenuName());
                pushDTO.setPathType(appRouter.getPathType());
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
                pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, false));
                List<PushedNotification> notifications = new ArrayList<PushedNotification>();

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
                    List<Device> device = new ArrayList<Device>();
                    for (String token : tokens)
                    {
                        device.add(new BasicDevice(token));
                    }
                    notifications = pushManager.sendNotifications(payLoad, device);
                }
                pushManager.stopConnection();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }
}
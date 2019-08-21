package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.AppRouterMapper;
import com.cqut.czb.bn.dao.mapper.AppRouterMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushNoticeMapperExtra;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePushNotice;
import com.cqut.czb.bn.service.vehicleService.RiderService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import java.util.ArrayList;
import java.util.List;



/**
 * @author: lyk
 * @date: 8/12/2019
 */
@Service
public class RiderServiceImpl implements RiderService {

    @Autowired
    CleanRiderMapperExtra cleanRiderMapper;
    @Autowired
    RemotePushMapperExtra remotePushMapperExtra;
    @Autowired
    RemotePushNoticeMapperExtra remotePushNoticeMapperExtra;
    @Autowired
    AppRouterMapper appRouterMapper;

    @Override
    public boolean deleteByPrimaryKey(String riderId) {
        return cleanRiderMapper.deleteByPrimaryKey(riderId);
    }

    @Override
    public List<CleanRider> selectAllRiders() {
        return cleanRiderMapper.selectAllRiders();
    }

    @Override
    public List<CleanRider> selectByStatus(Integer status) {
        return cleanRiderMapper.selectByStatus(status);
    }

    @Override
    public List<CleanRider> selectByName(String riderName) {
        return cleanRiderMapper.selectByName(riderName);
    }

    @Override
    public List<CleanRider> getRider(CleanRider record) {
        return cleanRiderMapper.getRider(record);
    }

    @Override
    public boolean insert(CleanRider record) {
        record.setRiderId(StringUtil.createId());
        return cleanRiderMapper.insert(record);
    }

    @Override
    public boolean insertSelective(CleanRider record) {
        record.setRiderId(StringUtil.createId());
        return cleanRiderMapper.insertSelective(record);
    }

    @Override
    public CleanRider selectByPrimaryKey(String riderId) {
        return cleanRiderMapper.selectByPrimaryKey(riderId);
    }

    @Override
    public boolean updateByPrimaryKeySelective(CleanRider record) {
        return cleanRiderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public boolean updateByPrimaryKey(CleanRider record) {
        return cleanRiderMapper.updateByPrimaryKey(record);
    }

    public void  sendMesToApp(String  noticeId, User user){
//    public static void  main(String[] args){
        RemotePush remotePush = remotePushMapperExtra.selectByUser(user.getUserId());
////        "768878996dc4f6fee4b367a24d609a0208088abcce88a4b86259b12a494b0817"
        String deviceToken = remotePush.getDeviceToken();
        RemotePushNotice remotePushNotice = remotePushNoticeMapperExtra.selectById(noticeId);
        String  alert  =remotePushNotice.getNoticeContent();//push的内容
//        String deviceToken = "768878996dc4f6fee4b367a24d609a0208088abcce88a4b86259b12a494b0817";
//        String  alert  ="有骑手接单了";//push的内容
        int badge = 1;//图标小红圈的数值
        String sound = "default";//铃音

        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);
        String certificatePath = "czb-bn\\bn-util\\src\\main\\java\\com\\cqut\\czb\\bn\\util\\certificate\\iosPush.p12";
        String certificatePassword = "renduoduo2019";//此处注意导出的证书密码不能为空因为空密码会报错
        boolean sendCount = true;

        try
        {
            PushNotificationPayload payLoad = new PushNotificationPayload();
            AppRouter appRouter = appRouterMapper.selectByPrimaryKey(remotePushNotice.getAppRouterId());
            payLoad.addCustomDictionary("appRouter",appRouter);
            payLoad.addAlert(alert); // 消息内容
            payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
//            payLoad.addCustomDictionary();

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
    };
}

package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapper;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.service.vehicleService.RiderService;
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
    CleanRiderMapper cleanRiderMapper;

    @Override
    public int deleteByPrimaryKey(String riderId) {
        return 0;
    }

    @Override
    public List<CleanRider> selectAllRiders() {
        return cleanRiderMapper.selectAllRiders();
    }

    @Override
    public int insert(CleanRider record) {
        cleanRiderMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(CleanRider record) {
        cleanRiderMapper.insert(record);
        return 0;
    }

    @Override
    public CleanRider selectByPrimaryKey(String riderId) {
        return cleanRiderMapper.selectByPrimaryKey(riderId);
    }

    @Override
    public int updateByPrimaryKeySelective(CleanRider record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(CleanRider record) {
        return 0;
    }


    public void  sendMesToApp(){
        String deviceToken = "ca481af1307b2c8e15e123cb00e0be845b56ff55a76f5d673702db576984b0db";
        String  alert  = "收到通知了";//push的内容
        int badge = 3;//图标小红圈的数值    
        String sound = "default";//铃音    
    
        List<String> tokens = new ArrayList<String>();    
        tokens.add(deviceToken);    
        String certificatePath = "czb-bn\\bn-util\\src\\main\\java\\com\\cqut\\czb\\bn\\util\\certificate\\certificate.p12";
        String certificatePassword = "1347929462xhd";//此处注意导出的证书密码不能为空因为空密码会报错
        boolean sendCount = true;    
  
        try    
        {    
            PushNotificationPayload payLoad = new PushNotificationPayload();    
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
              
            System.out.println("dadsadsadadada");  
            // 发送push消息    
            if (sendCount)    
            {    
                Device device = new BasicDevice();    
                device.setToken(tokens.get(0));    
                PushedNotification notification = pushManager.sendNotification(device, payLoad, true);    
                notifications.add(notification);    
                System.out.println("发生");  
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
        {    System.out.println("AAAAAAAAAAAAAAAAAAAAAA");  
            e.printStackTrace();    
        }    
    };
}

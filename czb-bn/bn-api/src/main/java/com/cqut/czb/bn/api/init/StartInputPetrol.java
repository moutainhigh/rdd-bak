package com.cqut.czb.bn.api.init;

import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class StartInputPetrol  implements CommandLineRunner  {


    @Autowired
    AppHomePageService appHomePageService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("开始放用卡");
        //开始放油卡
        if (appHomePageService.selectAllPetrol()) {
            System.out.println("有油卡"+ PetrolCache.AllpetrolMap.size()+ ":"+PetrolCache.currentPetrolMap.size());

        } else {
            System.out.println("无任何油卡");
        }

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<String,Petrol>> currentPetrol = PetrolCache.currentPetrolMap.entrySet().iterator();
                while(currentPetrol.hasNext()){
                    Map.Entry<String, Petrol> entry=currentPetrol.next();
                    String key=entry.getKey();
                    Petrol petrol=PetrolCache.currentPetrolMap.get(key);
                    if(petrol.getEndTime()==0||petrol.getEndTime()<=System.currentTimeMillis()){
                        System.out.println("有油卡"+ PetrolCache.AllpetrolMap.size()+ ":"+PetrolCache.currentPetrolMap.size());
                        currentPetrol.remove();        //OK
                        petrol.setOwnerId("");//将用户id置为空
                        petrol.setEndTime(0);
                        PetrolCache.AllpetrolMap.put(petrol.getPetrolNum(),petrol);
                        System.out.println("放回油卡，卡号："+petrol.getPetrolNum());
                        System.out.println("有油卡"+ PetrolCache.AllpetrolMap.size()+ ":"+PetrolCache.currentPetrolMap.size());
                    }
                }
            }
        }, 0, 10000, TimeUnit.MILLISECONDS);
    }
}

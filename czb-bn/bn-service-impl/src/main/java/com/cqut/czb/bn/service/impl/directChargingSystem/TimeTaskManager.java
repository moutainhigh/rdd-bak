package com.cqut.czb.bn.service.impl.directChargingSystem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

public class TimeTaskManager {
    Map timerTasks = new HashMap();
    AutoTimer autoTimer = null;
    private static TimeTaskManager timeTaskManager = new TimeTaskManager();

    public TimeTaskManager() {
        autoTimer = new AutoTimer();
    }

    /**
     * 添加定时任务
     * */
    public boolean addTimerTask(String taskCode,AutoTimerTask timerTask){
        synchronized(this){
            if(timerTasks.containsKey(taskCode.toLowerCase())){
                return false;
            }
            timerTask.setManager(timeTaskManager);
            timerTasks.put(taskCode,timerTask);
            autoTimer.schedule(timerTask);
        }
        return true;
    }
    /**
     * 删除定时任务
     * */
    public void deleteTimerTaskByCode(String taskCode,boolean cancelTask){
        Object obj=timerTasks.get(taskCode);
        if(obj!=null){
            if(cancelTask){
                TimerTask timerTask=(TimerTask)obj;
                timerTask.cancel();
            }
            timerTasks.remove(taskCode);
        }
    }

    public int findTimerTaskByCode(String taskCode){
        Object obj=timerTasks.get(taskCode);
        if (obj != null) {
            return 1;
        }
        return 0;
    }
}

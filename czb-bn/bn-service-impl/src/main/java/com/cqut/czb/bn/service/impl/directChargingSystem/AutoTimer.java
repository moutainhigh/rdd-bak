package com.cqut.czb.bn.service.impl.directChargingSystem;

import java.util.Timer;

public class AutoTimer extends Timer {
    public AutoTimer() {
    }
    public void schedule(AutoTimerTask timerTask){
        if (timerTask.getFirstDate() !=null){
            if (timerTask.getPeriod() > 0){
                this.schedule(timerTask,timerTask.getFirstDate(),timerTask.getPeriod());
            }else {
                this.schedule(timerTask,timerTask.getFirstDate());
            }
        }else {
            if (timerTask.getPeriod() > 0){
                this.schedule(timerTask,timerTask.getDelay(),timerTask.getPeriod());
            }else {
                this.schedule(timerTask,timerTask.getDelay());
            }
        }
    }
}

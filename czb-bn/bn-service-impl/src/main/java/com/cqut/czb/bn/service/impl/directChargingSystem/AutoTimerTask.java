package com.cqut.czb.bn.service.impl.directChargingSystem;

import java.util.Date;
import java.util.TimerTask;

public abstract class AutoTimerTask extends TimerTask {
    private String taskCode = "";
    long period=-1;//循环调用时间,为-1时无循环调用
    Date firstDate=null;//第一次执行时间
    long delay=0;//延迟执行时间（若有firstDate则delay无效）
    TimeTaskManager manager;
    boolean isStop =false;
    long stopTimes = -1;//重複次數，在到達這個次數之後循環將會停止

    abstract public void execute();

    public AutoTimerTask(String _taskCode) {
        if (!(_taskCode.trim().equals("") || _taskCode == null)) {
            this.taskCode = _taskCode;
        }
    }

    public void setStop(boolean isStop,long stopTimes){
        this.isStop = isStop;
        this.stopTimes = stopTimes;
    }

    @Override
    public void run() {
        execute();
        if (isStop) {
            stopTimes--;
            if (stopTimes < 0){
                isStop = false;
            }
        }
        if (manager != null && isStop && stopTimes == 0){
            System.out.println("删除Task" + taskCode);
            manager.deleteTimerTaskByCode(taskCode,isStop);
            manager=null;
        }
    }

    public void setManager(TimeTaskManager manager) {
        this.manager = manager;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "AutoTimerTask{" +
                "taskCode='" + taskCode + '\'' +
                ", period=" + period +
                '}';
    }
}

package com.cqut.czb.bn.service.impl.directChargingSystem;

import java.util.LinkedList;
import java.util.Queue;

public enum APIUP {
    ANDA888_OIL("anda888Oil"),
    ANDA888_PHONE("anda888Phone"),
    CHENXIE_OIL("chenxieOil"),
    CHENXIE_PHONE("chenxiePhone");

    final String value;

    APIUP(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    static final Queue<APIUP> oilq = new LinkedList<>();

    static final Queue<APIUP> phoneq = new LinkedList<>();

    static {
//        oilq.offer(ANDA888_OIL);
        oilq.offer(CHENXIE_OIL);
        phoneq.offer(ANDA888_PHONE);
        phoneq.offer(CHENXIE_PHONE);
    }

    public static APIUP getPhone(){
        APIUP res = phoneq.poll();
        phoneq.offer(res);
        return res;
    }

    public static APIUP getOil(){
        APIUP res = oilq.poll();
        oilq.offer(res);
        return res;
    }
}

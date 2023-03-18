package com.cqut.czb.bn.service.impl.directChargingSystem;

public enum YF_CONSTANT {

//    LT_100(3),
//    LT_200(4),
    // 不支持重庆湖南
//    YD_100(5),
//    YD_200(6),
    // 重庆专区
//    YDN_100(89),
//    YDN_200(90),
//    DX_100(7),
//    DX_200(8);

    YD_100(135),
    YD_200(136),
    DXN_100(137),
    DXN_200(138);



    private final Integer id;

    YF_CONSTANT(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Integer getID(Integer amount, String operator) {
        if (operator == null || operator.length() < 2) {
            return null;
        }
        if (operator.endsWith("电信")){
            if (amount == 100) {
                return DXN_100.id;
            } else if (amount == 200) {
                return DXN_200.id;
            }
        }
        if (operator.endsWith("移动")){

            if (amount == 100) {
                return YD_100.id;
            } else if (amount == 200) {
                return YD_200.id;
            }
        }
        return null;
    }
}

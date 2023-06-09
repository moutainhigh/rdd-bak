package com.cqut.czb.bn.util.constants;

public class SystemConstants {

    public static String EMPTY_STR = "";

    public static String COMMA_REGEX = ",";

    public static Double  VIP_PRICE = 0.01;

    public static final String IPAPI="https://www.ddcattom.com:8443";
    /**
     * 表示数据库中false的值
     * */
    public static byte FALSE_FLAG = 0;

    /**
     * 代表数据库中true的值
     * */
    public static byte TRUE_FLAG = 1;

    /*采购合同生成编号前缀*/
    public static String PURCHASECONTRACT_NO="CG";

    /*保养生成编号前缀*/
    public static String MAINTAIN_NO="BY";

    /*购买合同类型条件type*/
    /*合同结束日期和账单结束组*/
    public static String CONTRACT_CONDITION_TYPE_ENDTIME="1";
    /*质保结束日期和质保里程结束组*/
    public static String CONTRACT_CONDITION_TYPE_ENDMILEAGE="2";

    /*购买合同状态--待审核*/
    public static  Integer CONTRACT_STATION_WAIT=0;
    /*购买合同状态--审核通过*/
    public static Integer CONTRACT_STATION_COMPLETE=1;
    /*购买合同状态--审核失败*/
    public static Integer CONTRACT_STATION_FAIL=2;
    /*购买合同状态--待提交*/
    public static Integer CONTRACT_STATION_WAIT_COMMIT=3;
    /*购买合同状态--中止审核中*/
    public static Integer CONTRACT_STATION_TERMINATE_WAIT=4;
    /*购买合同状态--中止*/
    public static Integer CONTRACT_STATION_TERMINATE=5;
    /*购买合同状态显示文字*/
    public static  String CONTRACT_STATION_WAIT_STRING="待审核";
    public static  String CONTRACT_STATION_WAIT_COMMIT_STRING="待提交";
    public static  String CONTRACT_STATION_COMPLETE_STRING="执行中";
    public static  String CONTRACT_STATION_FAIL_STRING="审核失败";
    public static  String CONTRACT_STATION_COMMIT_STRING="审核通过";
    public static  String CONTRACT_STATION_TERMINATE_WAIT_STRING="中止审核中";
    public static  String CONTRACT_STATION_TERMINATE_STRING="中止";

    /*持有类型ID*/
    /*产权所有*/
    public static  String HOLD_TYPE_PROPERTY="1";
    /*使用权*/
    public static  String HOLD_TYPE_USE="2";

    /**
     * 导出个人收款记录excel的头部
     */
    public static final String[] PAY_TO_PERSON_RECORDS = {"合同号","收款人姓名","收款人身份证号","开户行","银行卡账号","应打款金额","实打款金额","打款状态","打款时间"};
    /**
     * 导出平台收款记录excel的头部
     */
    public static final String[] PLATFORM_INCOME_RECORDS = {"合同号","企业名字","应收款金额","实收款金额","目标月","企业打款时间","收款状态"};

    /**
     * 导出油卡寄送记录excel的excel头部
     */
    public static final String[] PETROL_DELIVERY_RECORD_HEAD = { "油卡编号", "油卡类型","寄送状态","收卡人姓名", "购卡时间","收卡人电话", "收卡主地址", "收卡详细地址","快递单号","快递公司" };
    /**
     * 导出充值记录excel的头部
     */
    public static final String[] PETROL_RECHARGE_EXCEL_HEAD={"油卡号码","油卡类型","充值金额","支付价格","充值状态","充值类别","电话号码","购卡时间","购卡方式"};

    /**
     * 导出充值记录excel的头部
     */
    public static final String[] PETROL_SALE_EXCEL_HEAD={"油卡号码","支付订单号","油卡类型","油卡面额","油卡价格","购买者电话","支付方式","交易时间","地区","充值类别"};

    /**
     * 导出中石化优惠券销售记录
     */
    public static final String[] PETROL_COUPONS_SALE_EXCEL_HEAD = {"油卡号码","璐付订单号","支付订单号","购买人电话","支付方式","充值状态","购买时间","充值金额","缴费订单编号","缴费交易流水编号","地区"};

    /**
     * 导出合伙人考核excel的头部
     */
    public static final String[] PARTNER_ASSESSMENT_EXCEL_HEAD = {"手机号码","合伙人类型","月目标推荐人数","月推荐人数","月目标首次消费人数","月新用户首次消费人数","状态","任务开始时间","任务结束时间"};

    /**
     * 导出商家结算Excel的头部
     */
    public static final String[] SETTLE_ORDER_EXCEL_HEAD = {"商品名称","订单号","商品价格","支付方式","第三方订单号","交易时间"};
    /**
     * 导出订单结算Excel的头部
     */
    public static final String[] OREDER_SETTEL_EXCEL_HEAD = {"商店名","结算金额(元)","订单号","用户昵称","用户手机号","商品名","支付价格(元)","第三方订单号","订单状态","结算状态","属性"};
    /**
     * 导出小程序订单管理Excel的头部
     */
    public static final String[] OREDER_MANAGE_EXCEL_HEAD = {"商店名","客户名称","客户电话","平台订单号","地址信息","商品名","商品数量","支付价格","支付状态","下单时间","快递公司","快递单号","快递状态","上级用户名","上级绑定电话","上上级用户名","上上级绑定电话"};
    /**
     * 导出自动充值记录Excel的头部
     */
    public static final String[] AUTOMATIC_RECHARGR_EXCEL_HEAD ={"序号","充值卡号","电话","用户类型","金额","价格","订单时间","执行时间","执行状态","执行信息"};
    /**
     * 导出自动充值记录中合计Excel的头部
     */
    public static final String[] SUM_AUTO_RECHARGE_SUCCESS_EXCEL_HEAD={"合计","充值成功人数","充值失败人数",  "总人数","总充值金额(成功)","总支付金额(成功)","导出时间段"};
    /**
     * 导出自动充值记录中合计Excel的头部
     */
    public static final String[] SUM_AUTO_RECHARGE_FAILED_EXCEL_HEAD={"合计","充值失败人数","充值成功人数","总人数","总充值金额(失败)","总支付金额(失败)","导出时间段"};
    /**
     * 导出VIP充值记录Excel的头部
     */
    public static final String[] VIP_RECHARGR_EXCEL_HEAD ={"账号","充值金额","地区","支付方式","第三方订单号","消费时间"};
    /**
     * 导出VIP充值记录中合计Excel的头部
     */
    public static final String[] SUM_VIP_FAILED_RECHARGE_EXCEL_HEAD={"充值总额","总单数"};
    /**
     * 导出线下大客户充值记录Excel的头部
     */
    public static final String[] RECHARGE_RECORDS_HEAD={"手机号","充值金额(元)","圈回金额(元)","余额(元)","充值时间"};
    /**
     * 导出线下大客户消费记录Excel的头部
     */
    public static final String[] CONSUMPTION_RECORDS_HEAD={"油卡号","消费金额(元)","充值状态","线下大客户电话","消费时间"};
    /**
     *导出线下大客户余额记录Excel的头部
     */
    public static final String[] CLIENT_RECORDS_HEAD={"手机号","充值总额(元)","消费支出(元)","圈回金额(元)","余额(元)","分配角色时间"};
    /**
     *导出微信商品库存Excel的头部
     */
    public static final String[] WXSTOCK_DETAILS_HEAD={"商品编号","商品名","属性","内容","价格(元)","状态","导入时间"};

    /**
     * 导出充值记录excel的头部
     */
    public static final String[] WITHOUT_CARD_PETROL_SALE_EXCEL_HEAD={"所属油卡卡码","支付订单号","账号","消费时间","所购买商品","消费金额","消费地点"};

    /**
     * 导出订单记录excel的头部
     */
    public static final String[] OIL_CARD_RECHARGE_EXCEL_HEAD={"订单号","第三方订单号","充值账户","持卡人手机号","充值金额","支付金额","充值状态","客户账号","充值时间","上级","运营商"};

    /**
     * 导出直充代理人记录excel的头部
     */
    public static final String[] DIRECT_AGENT_EXCEL_HEAD={"手机号","余额","消费支出","充值总额","圈回金额","分配角色时间"};

    /**
     * 导出水电费记录excel的头部
     */
    public static final String[] ELECTRICITY_EXCEL_HEAD={"户号","区域","充值金额","支付金额","充值人账号","充值时间","订单完成时间","充值状态","第三方账号"};

    /**
     * 导出直充代理人消费记录excel的头部
     */
    public static final String[] DIRECT_AGENT_CONSUMPTION_EXCEL_HEAD={"充值账号","消费金额","充值状态","代理人电话","消费时间"};

    /**
     * 导出直充代理人消费记录excel的头部
     */
    public static final String[] DIRECT_AGENT_RECHARGE_EXCEL_HEAD={"手机号","充值金额","圈回金额","余额","操作时间"};

    /**
     * 导出返佣记录excel的头部
     */
    public static final String[] FANYONG_EXCEL_HEAD={"消费账号","金额","分类","订单号","返佣账号","操作时间"};

    /**
     * 导出提现记录excel的头部
     */
    public static final String[] WITHDRAWALS_EXCEL_HEAD={"提现用户","提现金额","提现后金额","提现方式","提现时间"};

    public static final int PERSONAL_USER = 0;
    public static final int ENTERPRISE_USER = 1;
}

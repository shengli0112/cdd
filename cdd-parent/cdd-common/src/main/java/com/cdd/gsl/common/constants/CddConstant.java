package com.cdd.gsl.common.constants;

public class CddConstant {
    public static final int RESULT_SUCCESS_CODE = 1;

    public static final int RESULT_FAILD_CODE = 0;

    public static final int RESULT_CHANGE_DEVICE_CODE = 2;

    //普通经纪人
    public static final int COMMON_BROKER_TYPE = 1;

    //经纪人主管
    public static final int MANAGE_BROKER_TYPE = 2;

    public static final String MESSAGE_CONTENT = "您有一条新消息";

    public static final String MESSAGE_CONTENT_MATCH = "您发布的信息匹配成功,点击查看";

    public static final String MESSAGE_CHAT_TYPE = "chat";

    public static final String MESSAGE_HOUSE_TYPE = "house";

    public static final String MESSAGE_ENTRUST_TYPE = "entrust";

    public static final String MESSAGE_CURRENCY_TYPE = "currency";

    //置顶需要花费的币
    public static final Integer PAY_INTERGAL_TOP = 20;

    //置顶的天数
    public static final Integer TOP_DAY = 5;

    public static final Integer AWARD_CURRENCY_COUNT = 5;

    //厂房 仓库 土地
    public static final Integer HOUSE_TYPE_CF = 1;
    public static final Integer HOUSE_TYPE_CK = 2;
    public static final Integer HOUSE_TYPE_TD = 3;

    public static final String CONSUME_RECORD_TYPE_HOUSE = "house";
    public static final String CONSUME_RECORD_TYPE_ENTERPRISE = "enterprise";
    public static final String CONSUME_RECORD_TYPE_LEASE_PARK = "leasePark";
    public static final String CONSUME_RECORD_TYPE_SELL_PARK = "sellPark";

    public static final Integer CONSUME_RECORD_AWARD = 1; //奖励
    public static final Integer CONSUME_RECORD_CONSUME = 2;//消费

    public static final Integer CONSUME_RECORD_TYPE_ADD_HOUSE = 1;//添加房源
    public static final Integer CONSUME_RECORD_TYPE_TOP = 2; //置顶
    public static final Integer CONSUME_RECORD_TYPE_MEMBER = 3; //会员
    public static final Integer CONSUME_RECORD_TYPE_INFORM = 4; //举报


    public static final Integer INFORM_INTEGRAL_NUM = 20; //举报属实扣除积分数

    public static final String TOP_HOUSE_TITLE = "您置顶房源";
    public static final String TOP_PARK_TITLE = "您置顶园区";
    public static final String TOP_ENTERPRISE_TITLE = "您置顶企业圈";

    public static final String CREATE_HOUSE_TITLE = "您发布新房源";
    public static final String CREATE_PARK_TITLE = "您发布新园区";
    public static final String CREATE_ENTERPRISE_TITLE = "您发布新企业圈";

    /**
     * 注册奖励300多多币
     */
    public static final Integer AWARD_INTEGRAL = 300;

}

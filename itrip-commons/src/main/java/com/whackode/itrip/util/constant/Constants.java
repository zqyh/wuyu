package com.whackode.itrip.util.constant;

/***
 * 系统中保存的一些常量
 */
public class Constants {
    //默认起始页
    public static final Integer DEFAULT_PAGE_NO = 1;
    //默认页大小
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    //通用常量
    public static class Common{
        //否
        public final static Integer NOT = 0;
        //是
        public final static Integer YES = 1;
        //女
        public final static Integer SEX_WOMEN = 0;
        //男
        public final static Integer SEX_MAN = 1;
        //数据精度
        public final static Integer DECIMAL_DIGITS = 2;
    }
    //访问来源
    public static class SourceType{
        //访问来源-APP
        public final static Integer APP = 0;
        //访问来源-PC
        public final static Integer PC = 1;
    }
    //连接符
    public static class Connnector{
        //逗号
        public final static String COMMA_ = ",";
        //下划线
        public final static String UNDERLINE = "_";
        //冒号
        public final static String COLON=":";
    }
    //时长
    public static class Duration{
        //一秒
        public final static Integer SECOND_INT = 1;
        //一分钟
        public final static Integer MINUTE_INT = SECOND_INT * 60;
        //半小时
        public final static Integer HALF_HOUR_INT = MINUTE_INT * 30;
        //一小时
        public final static Integer HOUR_INT=MINUTE_INT*60;

    }
    //正则的一些常量
    public static class RegConstant{
        //手机号正则
        public static String PHONE_REGSTR = "^[1][0-9]{10}$";
        //密码正则
        public static String PASSWORD_REGSTR = "^([A-Z]|[a-z]|[0-9]|[_]){6,10}$";
    }
    //SMS相关常量
    public static class Sms{
        public static class CodeType{
            public static Integer LOGIN_OR_REGISTER=0;
            public static Integer PASS_UPDATE=1;
            public static Integer ORDER_NOTICE=2;
        }
    }
    //RedisKey相关的常量
    public static class RedisKey{
        public static String PROJECT_PRIFIX="roypro";
        public static String SMS_PRIFIX="sms";
        public static String TOKEN_PRIFIX="token";
    }

    //token相关的常量
    public static class Token{
        //token有效时间 2小时
        public final static Integer SESSION_TIMEOUT=Duration.HOUR_INT*2;
        //token置换保护时间 1小时
        public final static Integer REPLACEMENT_PROTECTION_TIMEOUT=Duration.HOUR_INT;
        //旧的token延期过期时间 2分钟
        public final static Integer REPLACEMENT_DELAY=Duration.MINUTE_INT*2;
    }

    //错误码相关
    public static class ErrorCode{
        /*认证模块错误码-start*/
        public final static String AUTH_UNKNOWN="30000";
        public final static String AUTH_USER_ALREADY_EXISTS="30001";//用户已存在
        public final static String AUTH_AUTHENTICATION_FAILED="30002";//认证失败
        public final static String AUTH_PARAMETER_ERROR="30003";//用户名密码参数错误，为空
        public final static String AUTH_ACTIVATE_FAILED="30004";//邮件注册，激活失败
        public final static String AUTH_REPLACEMENT_FAILED="30005";//置换token失败
        public final static String AUTH_TOKEN_INVALID="30006";//token无效
        public static final String AUTH_ILLEGAL_USERCODE = "30007";//非法的用户名
        public static final String AUTH_ACTIVATE_NO = "30008";//未激活
        public final static String AUTH_USER_REG_NO="30009";//未注册
        public final static String AUTH_USER_INCOMPLETE="30010";//信息不完成
    }

    //订单相关
    public static class OrderCode{
        public final static String ORDER_PROCESS_OK="{\"1\":\"订单提交\",\"2\":\"订单支付\",\"3\":\"支付成功\",\"4\":\"入住\",\"5\":\"订单点评\",\"6\":\"订单完成\"}";
        public final static String ORDER_PROCESS_CANCEL="{\"1\":\"订单提交\",\"2\":\"订单支付\",\"3\":\"订单取消\"}";
    }

}

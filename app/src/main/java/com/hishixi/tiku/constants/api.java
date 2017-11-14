package com.hishixi.tiku.constants;

/**
 * api地址
 *
 */
public class api {
    // API账户
    public static final String API_ACCOUNT_NUMER = "sxandroid";
    // API密码
    public static final String API_PASSWORD = "123456";
    // API账户与密码
    public static final String API_ACCOUNT_PASSWORD = "sxandroid123456";
    // pre&正式环境
    public static final String HOST_URL = "http://apipre.hishixi.cn";
    // 令牌：所有访问的入口
    public static final String POWER_URL = "/Auth/index";
    // 登陆
    public static final String LOGIN_URL = "/InsAccount/login";
    // 注册
//    public static final String REGISTER_URL = "/Account/studentRegist";
    // 注册时获取验证码
    public static final String REGISTER_GETCODE_URL = "/Account/sendSmsCode";
    // 找回密码
    public static final String FINDPSSWORD_URL = "/Account/retrievePassword";
    // 修改密码
    public static final String UPDATEPWD_URL = "/Account/changePwd";
    // 验证手机号
    public static final String TESTPHONENUMBER_URL = "/Account/verifyMobileStatus";
    //订单列表
    public static final String GET_ORDER_LIST = "/InsHome/orderList";
    //订单详情
    public static final String GET_ORDER_DETAIL = "/InsHome/orderInfo";
    //预约日期列表
    public static final String GET_BOOK_DATE_LIST = "/InsHome/bookDateList";
    //预约时间列表
    public static final String GET_BOOK_TIME_LIST = "/InsHome/bookTimeList";
    //添加预约时间
    public static final String ADD_BOOK_TIME = "/InsInput/addBookTime";
    //删除预约时间
    public static final String Delete_BOOK_TIME = "/InsInput/delBookTime";
    //获取通知列表
    public static final String GET_MESSAGE_LIST = "/InsHome/noticeList";
    //删除通知
    public static final String DELETE_MESSAGE = "/InsInput/delNotice";
    //更新阅读状态
    public static final String UPDATE_MESSAGE_READ_STATUS = "/InsInput/updateMsgStat";
    //获取个人资料
    public static final String GET_USER_INFO = "/InsHome/user";
    //更新个人资料
    public static final String UPDATE_USER_INFO_TXT = "/InsInput/updateUser";
    //更新头像
    public static final String UPDATE_USER_INFO_AVATAR = "/InsInput/updateAvatar";
    //首页数据
    public static final String GET_INDEX_PAGE = "/InsHome/index";
    //获取验证码
    public static final String GET_VERIFY_CODE = "/InsAccount/getCode";
    //修改密码
    public static final String MODIIFY_PASSWORD = "/InsAccount/setPwdSafe";
    //学生预约列表
    public static final String GET_STUDENT_RESERVE_LIST = "/InsHome/bookServiceList";
    //服务列表
    public static final String GET_SERVICE_LIST = "/InsHome/serviceList";
    //服务详情
    public static final String GET_SERVICE_DETAIL = "/InsHome/serviceInfo";
    //获取用户云信登录账号和密码
    public static final String GET_NIM_ACCOUNT = "/NeteaseIM/register";
    //创建云信聊天室
    public static final String CREATE_CHAT_ROOM = "/InsInput/pullVideoService";
    //关闭云信聊天室
    public static final String CLOSE_CHAT_ROOM = "/InsInput/offVideoService";

}

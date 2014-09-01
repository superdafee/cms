package com.zjs.cms.utils;

/**
 * Created with IntelliJ IDEA.
 * AccountUser: Emirate.Yang
 * Date: 12-11-7
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1;
    public static final int SALT_SIZE = 8;

    public static final int PAGE_SIZE = 20;

    public static final int UPLOAD_FILE_SIZE = 20 * 1000 * 1000;
    public static final int UPLOAD_AUDIO_SIZE = 10 *1000*1000;
    public static final int UPLOAD_WORKBOOK_SIZE = 1000 *1000*1000;
    public static final int UPLOAD_EXAM_DOC_SIZE = 10 *1000*1000;
    public static final String USER_TYPE_STUDENT = "ROLE_STUDENT";
    public static final String USER_TYPE_TEACHER = "ROLE_TEACHER";
    public static final String USER_TYPE_TUTOR = "ROLE_TUTOR";
    public static final String USER_TYPE_PARENTS = "ROLE_PARENTS";
    public static final String USER_TYPE_SYS_ADMIN = "ROLE_SYS_ADMIN";
    public static final String USER_TYPE_SCHL_ADMIN = "ROLE_SCHL_ADMIN";
    public static final String ADMIN_KPOINT = "ADMIN_KPOINT";
    public static final String ADMIN_USER = "ADMIN_USER";
    public static final String ADMIN_VIDEO = "ADMIN_VIDEO";
    public static final String ADMIN_QUIZ = "ADMIN_QUIZ";
    public static final String ADMIN_VIP = "ADMIN_VIP";
    public static final String ADMIN_WORKBOOK = "ADMIN_WORKBOOK";
    public static final String ADMIN_SYSWORKBOOK = "ADMIN_SYSWORKBOOK";
    public static final String ADMIN_RESOURCE = "ADMIN_RESOURCE";
    public static final String DEFAULT_REGION_CODE_CITY = "130100";
    public static final String DEFAULT_REGION_CODE_PROVINCE = "130000";
    public static final Long DEFAULT_QUESTION_DURATION = 120000L;
    public static final String DEFAULT_PASSWORD = "123456";
    public static final String DEFAULT_PASSWORD_PARENTS = "admin1";
    public static final int FILTE_RGRADE = 3;
    public static final String DES_KEY = "chmiot2012ischool";
    public static final String SEARCH_TIP = "ischool";
//    public static final String BASE_VIDEO_PATH = "attached/video";

    // 默认开学月份
    public static final int DEFAULT_TERM_BEGINS_MONTH = 8;
    // 默认下个学期开学
    public static final int DEFAULT_NEXT_TERM_BEGINS_MONTH = 2;
    //返回成功
    public static final String SUCCESS = "1";
    //返回失败
    public static final String FAIL = "0";

    public static final int COURSE_SHUXUE = 1;
    public static final int COURSE_YUWEN = 2;
    public static final int COURSE_YINGYU = 3;

    // SMS短信接口
    public static final String SERVICE_CODE = "MHE0010501";
    public static final String SMS_MMZH = "MMZH";
    public static final String SMS_DYISCHOOL = "DYISCHOOL";
    public static final String SMS_QRDYISCHOOL = "QRDYISCHOOL";
    public static final String SMS_QXDYISCHOOL = "QXDYISCHOOL";

    public static final Integer CATEGORY_QBANK =1 ;
    public static final Integer CATEGORY_QWORKBOOK =2 ;

    // 前置机数据同步记录--传输方向
    public static final int TO_FRONT = 1;
    public static final int FROM_FRONT = 2;
    //手机端设置tags
    public static final String PREFIX_PARENTS = "P_";
    public static final String PREFIX_CLASS = "C_";
    public static final String PREFIX_SCHOOL = "S_";
    public static final String PREFIX_STUDENT = "STU_";
}

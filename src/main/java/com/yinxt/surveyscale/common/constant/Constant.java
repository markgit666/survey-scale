package com.yinxt.surveyscale.common.constant;

/**
 * 常量类
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-23 21:45
 */
public class Constant {
    /**
     * 分隔符（附件/题目顺序）
     */
    public static final String NORMAL_SPLIT = "%";
    /**
     * 选择题选项/多选题答案分隔符
     */
    public static final String ITEMS_SPLIT = "@-@";
    /**
     * 量表id前缀
     */
    public static final String SCALE_PREFIX = "SL";
    /**
     * 医生id前缀
     */
    public static final String DOCTOR_PREFIX = "DR";
    /**
     * redis缓存注册验证码前缀
     */
    public static final String REDIS_REGISTER_PREFIX = "register_";
    /**
     * redis缓存修改密码验证码前缀
     */
    public static final String REDIS_MODIFY_PASSWORD_PREFIX = "modifyPassword_";
    /**
     * redis缓存图片验证码前缀
     */
    public static final String REDIS_CAPTCHA_PREFIX = "captcha_";
    /**
     * 单选题类型
     */
    public static final String radio_type = "radio";
    /**
     * 多选题类型
     */
    public static final String checkBox_type = "checkBox";
    /**
     * 问答题类型
     */
    public static final String QandA_type = "QandA";
    /**
     * 画图题类型
     */
    public static final String draw_type = "draw";
    /**
     * 图片题类型
     */
    public static final String picture_type = "picture";
    /**
     * 是
     */
    public static final String YES = "1";
    /**
     * 否
     */
    public static final String NO = "0";
    /**
     * 加盐
     */
    public static final int salt = 3;
}

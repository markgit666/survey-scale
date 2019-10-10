package com.yinxt.surveyscale.common.email.pojo;

import lombok.Data;

/**
 * 邮件信息
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-03 17:10
 */
@Data
public class EmailInfo {

    /**
     * 发件人
     */
    private String sendFrom;
    /**
     * 收件人
     */
    private String sendTo;
    /**
     * 邮件标题
     */
    private String title;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 发送时间
     */
    private Data sendDate;

}

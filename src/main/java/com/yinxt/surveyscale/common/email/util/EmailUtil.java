package com.yinxt.surveyscale.common.email.util;

import com.yinxt.surveyscale.common.email.pojo.EmailInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author yinxt
 * @version 1.0
 * @date 2019-10-03 17:18
 */
@Service
public class EmailUtil {

    @Value("${mail.smtp.host}")
    private String mailSmtpHost;
    @Value("${mail.user.address}")
    private String mailUserAddress;
    @Value("${mail.user.password}")
    private String mailUserPassword;
    @Value("${web.chn.name}")
    private String webCHNName;
    @Value("${mail.smtp.port}")
    private String smtpPort;

    /**
     * 邮件协议
     */
    private static final String protocol = "smtp";
    /**
     * 邮件内容类型
     */
    private static final String contentType = "text/html;charset=UTF-8";
    /**
     * 字符集
     */
    private static final String charset = "utf-8";

    public void send(EmailInfo emailInfo) throws Exception {
        Properties properties = new Properties();
        //使用的协议
        properties.setProperty("mail.transport.protocol", protocol);
        //smtp服务器地址
        properties.setProperty("mail.smtp.host", mailSmtpHost);
        //需要请求认证
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session = Session.getInstance(properties);
        session.setDebug(true);
        //创建邮件
        MimeMessage message = createMimeMessage(session, emailInfo);
        Transport transport = session.getTransport();
        //连接邮件服务器
        transport.connect(mailUserAddress, mailUserPassword);
        //发送
        transport.sendMessage(message, message.getAllRecipients());
    }

    /**
     * 创建邮件
     *
     * @param session
     * @param emailInfo
     * @return
     * @throws Exception
     */
    public MimeMessage createMimeMessage(Session session, EmailInfo emailInfo) throws Exception {
        MimeMessage mimeMessage = new MimeMessage(session);
        //发送人
        mimeMessage.setFrom(new InternetAddress(mailUserAddress, webCHNName, charset));
        //收件人
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, emailInfo.getSendTo());
        //主题
        mimeMessage.setSubject(emailInfo.getTitle(), charset);
        //邮件内容
        mimeMessage.setContent(emailInfo.getContent(), contentType);
        return mimeMessage;
    }

}

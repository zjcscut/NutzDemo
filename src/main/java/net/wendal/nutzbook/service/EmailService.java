package net.wendal.nutzbook.service;

import org.apache.commons.mail.HtmlEmail;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zhangjinci
 * @version 2016/6/7 14:29
 */
@IocBean(name = "emailService")
public class EmailService {

    private static final Log log = Logs.get();

    @Inject("refer:$ioc")
    private Ioc ioc;

    public boolean send(String to, String subject, String html) {
        try {
            HtmlEmail email = ioc.get(HtmlEmail.class);
            email.setSubject(subject);
            email.setHtmlMsg(html);
            email.addTo(to);
            email.buildMimeMessage();
            email.sendMimeMessage();
            return true;
        } catch (Throwable e) {
            log.info("send email fail", e);
            return false;
        }
    }
}

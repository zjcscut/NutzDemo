package net.wendal.nutzbook.mvc;

import org.nutz.lang.Stopwatch;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangjinci
 * @version 2016/6/7 10:51
 */
public class LogTimeProcessor extends AbstractProcessor {

    private static final Log log = Logs.get();

    public LogTimeProcessor() {
    }


    @Override
    public void process(ActionContext actionContext) throws Throwable {
        Stopwatch sw = Stopwatch.begin();
        try {
            doNext(actionContext);
        } finally {
            sw.stop();
            if (log.isDebugEnabled()) {
                HttpServletRequest req = actionContext.getRequest();
                log.debugf("[%4s]URI=%s %sms", req.getMethod(), req.getRequestURI(), sw.getDuration());
            }
        }
    }
}

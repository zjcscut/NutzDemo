package net.wendal.nutzbook.module;

import net.wendal.nutzbook.service.SqlTestService;
import net.wendal.nutzbook.util.RequestUtil;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangjinci
 * @version 2016/6/24 12:40
 */
@IocBean
@At("/test/sqltest/")
public class SqlTestModule {

    @Inject
    private SqlTestService sqlTestService;

    @At("list")
    public Object list(HttpServletRequest request) {
        Map<String, String> params = RequestUtil.params(request);
        return sqlTestService.list(params);
    }

}

package net.wendal.nutzbook.module;

import net.wendal.nutzbook.bean.User;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

/**
 * @author zhangjinci
 * @version 2016/6/6 18:32
 */
@IocBean
@At("/user")
@Ok("json")
@Fail("http:500")
public class UserModule {

    @Inject
    private Dao dao;

    @At("/count")
    private int count() {
        return dao.count(User.class);
    }
}

package net.wendal.nutzbook;

import net.wendal.nutzbook.bean.User;
import net.wendal.nutzbook.service.AuthorityService;
import net.wendal.nutzbook.service.UserService;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import java.util.Date;

/**
 * @author zhangjinci
 * @version 2016/6/6 17:59
 */
public class MainSetup implements Setup {

    @Override
    public void init(NutConfig nutConfig) {
        Ioc ioc = nutConfig.getIoc();
        ioc.get(NutQuartzCronJobFactory.class);
        Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao, "net.wendal.nutzbook", false);
        //重建数据库
        Daos.migration(dao, User.class, true, false);

        // 初始化默认根用户
        User admin = dao.fetch(User.class, "admin");
        if (admin == null) {
            UserService us = ioc.get(UserService.class);
            admin = us.add("admin", "123456");
        }

        AuthorityService as = ioc.get(AuthorityService.class);
        as.initFormPackage("net.wendal.nutzbook");
        as.checkBasicRoles(admin);
    }

    @Override
    public void destroy(NutConfig nutConfig) {

    }
}

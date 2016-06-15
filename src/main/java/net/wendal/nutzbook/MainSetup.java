package net.wendal.nutzbook;

import net.wendal.nutzbook.bean.*;
import net.wendal.nutzbook.service.AuthorityService;
import net.wendal.nutzbook.service.UserService;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

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

        //初始化五张表
        dao.create(ProUser.class, false);
        dao.create(ProAddress.class, false);
        dao.create(ProCompany.class, false);
        dao.create(ProRelation.class,false);
        dao.create(ProScope.class,false);
    }

    @Override
    public void destroy(NutConfig nutConfig) {

    }
}

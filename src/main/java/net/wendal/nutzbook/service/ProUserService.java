package net.wendal.nutzbook.service;

import net.wendal.nutzbook.bean.ProCompany;
import net.wendal.nutzbook.bean.ProScope;
import net.wendal.nutzbook.bean.ProUser;
import net.wendal.nutzbook.common.BaseDao;
import net.wendal.nutzbook.util.PageModel;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjinci
 * @version 2016/6/15 11:54
 */
@IocBean(name = "proUserService")
public class ProUserService {

    @Inject
    private BaseDao baseDao;

    @Inject
    private Dao dao;

    public PageModel<ProUser> queryByPage(int currentPage, int pageSize, Map<String, String> params) {
//        System.out.println("params map -- >" + params);
        Pager pager = new Pager();
        if (currentPage > 0) pager.setPageNumber(currentPage);
        if (pageSize > 0) pager.setPageSize(pageSize);

        Cnd cnd = Cnd.NEW();
        cnd.where().and("isDelete", "=", 0);
        if (params != null) {
            if (StringUtils.isNotEmpty(params.get("user_id"))) {
                cnd.and("id", "=", params.get("user_id"));
            }

            if (StringUtils.isNotEmpty(params.get("birth"))) {
                cnd.and("birth", "=", params.get("birth"));
            }

            if (StringUtils.isNotEmpty(params.get("user_name"))) {
                cnd.and("name", "like", "%" + params.get("user_name") + "%");
            }
            if (StringUtils.isNotEmpty(params.get("phone"))) {
                cnd.and("phone", "=", params.get("phone"));
            }

            if (StringUtils.isNotEmpty(params.get("email"))) {
                cnd.and("email", "=", params.get("email"));
            }

            if (StringUtils.isNotEmpty(params.get("is_enable"))) {
                cnd.and("isEnable", "=", Integer.valueOf(params.get("is_enable")));
            }
        }
        List<ProUser> list = dao.query(ProUser.class, cnd, pager);
        int count = dao.count(ProUser.class, cnd);
        return new PageModel<>(list, count, currentPage, pageSize);
    }


    public ProUser queryUserById(int id) {
        return dao.fetch(ProUser.class, id);
    }

    public void deleteUserById(int id) {
        ProUser user = dao.fetch(ProUser.class, id);
        user.setIsDelete(1);
        dao.update(user);
    }

    public List<ProScope> queryByPid(int pid) {
        Cnd cnd = Cnd.NEW();
        cnd.where().and("isDelete", "=", 0).and("pid", "=", pid);
        return dao.query(ProScope.class, cnd);
    }

    public List<ProCompany> queryAll(int currentPage, int pageSize) {
        Pager pager = new Pager();
        if (currentPage > 0) pager.setPageNumber(currentPage);
        if (pageSize > 0) pager.setPageSize(pageSize);
        Cnd cnd = Cnd.NEW();
        cnd.where().and("isDelete", "=", 0);
        return dao.query(ProCompany.class, cnd, pager);
    }


}

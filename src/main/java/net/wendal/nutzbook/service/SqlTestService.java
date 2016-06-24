package net.wendal.nutzbook.service;

import net.wendal.nutzbook.bean.ProUser;
import net.wendal.nutzbook.common.SqlContextUtils;
import net.wendal.nutzbook.util.PageModel;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjinci
 * @version 2016/6/24 12:40
 */
@IocBean
public class SqlTestService {

    @Inject
    private Dao dao;

    public PageModel<ProUser> list(Map<String, String> params) {
        Pager pager = new Pager();
        Criteria criteria = Cnd.cri();
        SqlContextUtils.buildCriteriaByParams(params, "find.name", "name", "LIKE", criteria);
        SqlContextUtils.buildCriteriaByParams(params, "find.id", "id", "IN", criteria);
        List<ProUser> list = dao.query(ProUser.class, criteria, pager);
        int count = dao.count(ProUser.class, criteria);
        return new PageModel<>(list, count, pager.getPageNumber(), pager.getPageSize());
    }
}

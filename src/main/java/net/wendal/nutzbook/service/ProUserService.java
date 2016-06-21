package net.wendal.nutzbook.service;

import net.wendal.nutzbook.bean.ProCompany;
import net.wendal.nutzbook.bean.ProRelation;
import net.wendal.nutzbook.bean.ProScope;
import net.wendal.nutzbook.bean.ProUser;
import net.wendal.nutzbook.common.BaseDao;
import net.wendal.nutzbook.util.PageModel;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.*;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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

    public List<ProUser> queryAllUsers() {
        Cnd cnd = Cnd.NEW();
        cnd.and("isDelete", "=", 0);
        return dao.query(ProUser.class, cnd);
    }

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
                cnd.and("email", "LIKE", "%" + params.get("email") + "%");
            }

            if (StringUtils.isNotEmpty(params.get("is_enable"))) {
                if ("all".equals(params.get("is_enable"))) {
                    cnd.and("isEnable", "IN", "0,1");
                } else {
                    cnd.and("isEnable", "=", Integer.valueOf(params.get("is_enable")));
                }
            }
        }
        List<ProUser> list = dao.query(ProUser.class, cnd, pager);
        int count = dao.count(ProUser.class, cnd);
        return new PageModel<>(list, count, currentPage, pageSize);
    }


    public ProUser queryUserById(int id) {
        return dao.fetch(ProUser.class, id);
    }

    public int deleteUserById(int id) {
        ProUser user = dao.fetch(ProUser.class, id);
        user.setIsDelete(1);
        return dao.updateIgnoreNull(user);
    }

    public Sql deleteUserByIds(Integer[] ids) {
        Sql sql = Sqls.create("update pro_user set is_delete = '1' where id in (@ids)");
        sql.setParam("ids", ids);
        dao.execute(sql);
        return sql;
    }

    public List<ProScope> queryByPid(int pid) {
        Cnd cnd = Cnd.NEW();
        cnd.where().and("isDelete", "=", 0).and("pid", "=", pid);
        return dao.query(ProScope.class, cnd);
    }

//    public List<ProCompany> queryAll(int currentPage, int pageSize) {
//        Pager pager = new Pager();
//        if (currentPage > 0) pager.setPageNumber(currentPage);
//        if (pageSize > 0) pager.setPageSize(pageSize);
//        Cnd cnd = Cnd.NEW();
////        cnd.where().and("isDelete", "=", 0);
//        return dao.query(ProCompany.class, null, pager);
//    }

    public PageModel<ProCompany> queryCompany(int currentPage, int pageSize, Map<String, String> params) {
        Pager pager = new Pager();
        if (currentPage > 0) pager.setPageNumber(currentPage);
        if (pageSize > 0) pager.setPageSize(pageSize);
        Cnd cnd = Cnd.NEW();

        if (params != null) {
            if (StringUtils.isNotEmpty(params.get("id"))) {
                cnd.and("id", "=", Integer.valueOf(params.get("id")));
            }
            if (StringUtils.isNotEmpty(params.get("name"))) {
                cnd.and("name", "LIKE", "%" + params.get("name") + "%");
            }
        }
        List<ProCompany> list = dao.query(ProCompany.class, cnd, pager);
        int count = dao.count(ProCompany.class, cnd);
        return new PageModel<>(list, count, currentPage, pageSize);
    }

    public int updateUser(ProUser user) {
//        System.out.println("IsEnable的值 ---- > " + user.getIsEnable());
        if (user.getIsEnable() != null && user.getIsEnable() == 1) {
            user.setEnableDesc("有效");
        }

        if (user.getIsEnable() != null && user.getIsEnable() == 0) {
            user.setEnableDesc("无效");
        }
        return dao.updateIgnoreNull(user);
    }

    public boolean updateUserRelation(List<ProRelation> proRelationList, int uid) {
        Cnd cnd = Cnd.NEW();
        cnd.where().and("userId", "=", uid);
        dao.clear(ProRelation.class, cnd);

        if (proRelationList != null && proRelationList.size() > 0) {
            for (ProRelation pr : proRelationList) {
                pr.setUserId(uid);
                pr.setIsDelete(0);
            }
        }
        return dao.insert(proRelationList) != null;
    }

    public ProUser insertNewUser(ProUser user) {
        user.setIsDelete(0);
        user.setEnableDesc("有效");
        user.setIsEnable(1);
        user.setCreateTime(new Date());
        return dao.insert(user);
    }

//    public int updateUser(ProUser user){
//        return dao.updateIgnoreNull(user);
//    }

    public PageModel<ProRelation> queryRelationByUserId(int uid) {
        Pager pager = new Pager();
        Cnd cnd = Cnd.NEW();
        cnd.and("userId", "=", uid);
        cnd.and("isDelete", "=", "0");
        List<ProRelation> list = dao.query(ProRelation.class, cnd, pager);
        int count = dao.count(ProRelation.class, cnd);
        return new PageModel<>(list, count, pager.getPageNumber(), pager.getPageSize());
    }


    public PageModel<ProRelation> queryRelationByCondition(Map<String, String> params, int pageNo, int pageSize) {
        Pager pager = new Pager();
        if (pageNo > 0) pager.setPageNumber(pageNo);
        if (pageSize > 0) pager.setPageSize(pageSize);
        Cnd cnd = Cnd.NEW();
        cnd.and("isDelete", "=", "0");
        if (params != null) {
            if (StringUtils.isNotEmpty(params.get("id"))) {
                cnd.and("id", "=", Integer.valueOf(params.get("id")));
            }
            if (StringUtils.isNotEmpty(params.get("name"))) {
                cnd.and("name", "LIKE", "%" + params.get("name") + "%");
            }
        }

        List<ProRelation> list = dao.query(ProRelation.class, cnd, pager);
        int count = dao.count(ProRelation.class, cnd);
        return new PageModel<>(list, count, pageNo, pageSize);
    }

    public ProRelation relationAdd(ProRelation proRelation) {
        proRelation.setIsDelete(0);
        return dao.insert(proRelation);
    }


    public List<ProRelation> queryRelationByUid(int uid) {
        Cnd cnd = Cnd.NEW();
        cnd.where().and("userId", "=", uid);
        return dao.query(ProRelation.class, cnd);
    }

    public ProRelation queryRelationById(int id) {
        return dao.fetch(ProRelation.class, id);
    }

    public int relationEdit(ProRelation proRelation) {
        return dao.updateIgnoreNull(proRelation);
    }

    public int relationDelete(int id) {
        ProRelation proRelation = dao.fetch(ProRelation.class, id);
        proRelation.setIsDelete(1);
        return dao.updateIgnoreNull(proRelation);
    }

}

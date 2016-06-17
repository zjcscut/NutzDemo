package net.wendal.nutzbook.module;

import net.wendal.nutzbook.bean.ProRelation;
import net.wendal.nutzbook.bean.ProUser;
import net.wendal.nutzbook.service.ProUserService;
import net.wendal.nutzbook.util.BaseAction;
import net.wendal.nutzbook.util.ExportExcelAction;
import net.wendal.nutzbook.util.RequestUtil;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.view.JspView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjinci
 * @version 2016/6/15 10:54
 */
@IocBean
@At("/userManage")
//@Ok("json:{locked:'password|salt',ignoreNull:true}") //不输出password和salt的值，忽略空值
@Fail("http:500")
public class UserManage extends BaseAction {

    private final static String path = "/view/user";

    @Inject
    private ProUserService proUserService;


    @At("/list")
    public View list(@Param("currentPage") int pageNum, @Param("numPerPage") int numPerPage, HttpServletRequest request) {
        request.setAttribute("code", 1);
        request.setAttribute("pm", proUserService.
                queryByPage(pageNum, numPerPage, RequestUtil.params(request)));
        return new JspView(path + "/list");
    }

    @At("/query")
    public View query(@Param("currentPage") int currentPage, @Param("numPerPage") int pageSize, HttpServletRequest request) {
        request.setAttribute("code", 1);
        request.setAttribute("pm", proUserService.
                queryByPage(currentPage, pageSize, RequestUtil.params(request)));
        return new JspView(path + "/list");
    }


    @At("/delete")
    @Ok("json")
    public Map<String, String> delete(@Param("id") int id) {
        String code = "200";
        String msg = "删除用户成功";
        if (proUserService.deleteUserById(id) <= 0) {
            code = "201";
            msg = "删除用户失败";
        }
        return getJsonResult(code, msg, "user_table_page");
    }

    @At("/delete/list")
    @Ok("json")
    public Map<String, String> deleteList(@Param("ids") Integer[] ids) {
        String code = "200";
        String msg = "批量删除用户成功";
        if (proUserService.deleteUserByIds(ids) == null) {
            code = "201";
            msg = "批量删除用户失败";
        }
        return getJsonResult(code, msg, "user_table_page");
    }

    //返回添加用户的页面 带回省级地址信息
    @At("/add/page")
    public View addPage(HttpServletRequest request) {
        request.setAttribute("regionList", proUserService.queryByPid(0));
        return new JspView(path + "/add");
    }

    @At("/add")
    @Ok("json")
    public Map<String, String> add(@Param("..") ProUser user) {
        proUserService.insertNewUser(user);
        String code = "200";
        String msg = "添加新用户成功";
        if (user == null) {
            code = "201";
            msg = "添加新用户失败";
        }
        return getJsonResult(code, msg, "user_table_page");
    }

    @At("/query/area")
    @Ok("json")
    public Object queryArea(@Param("pid") int pid) {
//		NutMap nutMap = NutMap.NEW();
//		nutMap.setv("area", proUserService.queryByPid(pid));
//		System.out.println("nutMap--> " + nutMap);
//		return nutMap;
        return proUserService.queryByPid(pid);
    }


    @At("/company/list")
    public View lookupCompany(@Param("currentPage") int currentPage, @Param("numPerPage") int pageSize, HttpServletRequest request) {
//        NutMap nutMap = NutMap.NEW();
        request.setAttribute("pm", proUserService.queryCompany(currentPage, pageSize, RequestUtil.params(request)));
        return new JspView(path + "/company");
    }

    @At("/company/search")
    public View searchCompany(@Param("currentPage") int currentPage, @Param("numPerPage") int pageSize, HttpServletRequest request) {
        Map<String, String> params = RequestUtil.params(request);
        request.setAttribute("pm", proUserService.queryCompany(currentPage, pageSize, params));
        return new JspView(path + "/company");
    }


    @At("/edit/page")
    public View editPage(@Param("id") int id, HttpServletRequest request) {
        request.setAttribute("user", proUserService.queryUserById(id));
        request.setAttribute("regionList", proUserService.queryByPid(0));
        return new JspView(path + "/edit");
    }

    @At("/save")
    @Ok("json")
    public Object saveEditUser(@Param("..") ProUser user) {
        String code = "200";
        String msg = "保存用户信息成功";
        if (proUserService.updateUser(user) <= 0) {
            code = "201";
            msg = "保存用户信息失败";
        }
        return getJsonResult(code, msg, "user_table_page");
    }

    //导出Excel（记得加上@Ok("raw")，否则会当成jsp渲染，导致ResponseWriter和OutputStream冲突）
    @At("/exportExcel")
    @Ok("raw")
    public void exportUserList(HttpServletResponse response) {
        List<ProUser> list = proUserService.queryAllUsers();
        daExportExcel(list, response);
    }

    @At("/relation/page")
    public View relationList(@Param("uid") int uid, HttpServletRequest request) {
        request.setAttribute("pm", proUserService.queryRelationByUserId(uid));
        request.setAttribute("uid", uid);
        return new JspView(path + "/relation");
    }


    @At("/relation/add/page")
    public View relationAddPage(@Param("uid") int uid, HttpServletRequest request) {
        request.setAttribute("uid", uid);
        System.out.println("uid-->" + uid);
        return new JspView(path + "/relation_add");
    }

    @At("/relation/add")
    @Ok("json")
    public Map<String, String> relationAdd(@Param("..") ProRelation proRelation) {
        String code = "200";
        String msg = "新增亲属信息成功";
        if (proUserService.relationAdd(proRelation) == null) {
            code = "201";
            msg = "新增亲属信息失败";
        }

        return getJsonResult("200", "新增亲属信息成功", "relation_page");
    }

    @At("/relation/edit/page")
    public View relationEditPage(@Param("id") int id, HttpServletRequest request) {
        request.setAttribute("pre", proUserService.queryRelationById(id));
        return new JspView(path + "/relation_edit");
    }

    @At("/relation/edit")
    @Ok("json")
    public Map<String, String> relationEdit(@Param("..") ProRelation proRelation) {
        String code = "200";
        String msg = "修改亲属信息成功";
        if (proUserService.relationEdit(proRelation) <= 0) {
            code = "201";
            msg = "修改亲属信息失败";
        }

        return getJsonResult("200", "修改亲属信息成功", "relation_page");
    }


    @At("/relation/search")
    public View relationSearch(HttpServletRequest request
            , @Param("currentPage") int currentPage, @Param("numPerPage") int pageSize, int uid) {
        Map<String, String> params = RequestUtil.params(request);
        request.setAttribute("pm", proUserService.queryRelationByCondition(params, currentPage, pageSize));
        request.setAttribute("uid", uid);
        return new JspView(path + "/relation");
    }

    @At("/relation/delete")
    @Ok("json")
    public Map<String, String> relationDelete(@Param("id") int id) {
        String code = "200";
        String msg = "删除亲属信息成功";
        if (proUserService.relationDelete(id) <= 0) {
            code = "201";
            msg = "删除亲属信息失败";
        }
        return getJsonResult(msg, code, "relation_page");
    }


}

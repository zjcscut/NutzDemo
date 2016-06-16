package net.wendal.nutzbook.module;

import net.wendal.nutzbook.bean.ProUser;
import net.wendal.nutzbook.service.ProUserService;
import net.wendal.nutzbook.util.BaseAction;
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
import java.util.HashMap;
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
    public View list(HttpServletRequest request) {
        request.setAttribute("code", 1);
        request.setAttribute("pm", proUserService.
                queryByPage(1, 20, RequestUtil.params(request)));
        return new JspView(path + "/list");
    }

    @At("/query")
    public View query(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize, HttpServletRequest request) {
        request.setAttribute("code", 1);
        request.setAttribute("pm", proUserService.
                queryByPage(currentPage, pageSize, RequestUtil.params(request)));
        return new JspView(path + "/list");
    }


    @At("/delete")
    @Ok("json")
    public Map<String, String> delete(@Param("id") int id) {
        proUserService.deleteUserById(id);
        return getJsonResult("200", "删除用户成功", "user_table_page");
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
    public View lookupCompany(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize, HttpServletRequest request) {
//        NutMap nutMap = NutMap.NEW();
        request.setAttribute("pm", proUserService.queryCompany(currentPage, pageSize, RequestUtil.params(request)));
        return new JspView(path + "/company");
    }

    @At("/company/search")
    public View searchCompany(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize, HttpServletRequest request) {
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
        if (proUserService.insertNewUser(user) == null) {
            code = "300";
            msg = "保存用户信息失败";
        }
        return getJsonResult(code, msg, "user_table_page");
    }


}

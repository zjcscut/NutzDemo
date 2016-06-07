package net.wendal.nutzbook.module;

import net.wendal.nutzbook.bean.User;
import net.wendal.nutzbook.bean.UserProfile;
import net.wendal.nutzbook.service.UserService;
import net.wendal.nutzbook.util.Toolkit;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.aop.interceptor.ioc.TransAop;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Scope;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author zhangjinci
 * @version 2016/6/6 18:32
 */
@IocBean
@At("/user")
//不输出password和salt的值，忽略空值
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
//Session校验。如果Session里面的me的值为空，直接跳回"/"
//@Filters(@By(type=CheckSession.class, args={"me", "/"}))
public class UserModule extends BaseModule {

    @Inject
    protected UserService userService;

    @At("/count")
    public int count() {
        return dao.count(User.class);
    }

    @At("/login")
    //空过滤器，防止checkSession
    @POST
    public Object login(@Param("username") String name,
                        @Param("password") String password,
                        @Param("captcha") String captcha,
                        @Attr(scope = Scope.SESSION, value = "nutz_captcha") String _captcha,
                        HttpSession session) {
        NutMap re = new NutMap();
        if (!Toolkit.checkCaptcha(_captcha, captcha)) {
            return re.setv("ok", false).setv("msg", "验证码错误");
        }
        int userId = userService.fetch(name, password);
        if (userId < 0) {
            return re.setv("ok", false).setv("msg", "用户名或密码错误");
        } else {
            session.setAttribute("me", userId);
            return re.setv("ok", true);
        }
    }

//    @At
//    @Ok(">>:/")
//    public void logout(HttpSession session) {
//        session.invalidate();
//    }


    protected String checkUser(User user, boolean create) {
        if (user == null) {
            return "空对象";
        }
        if (create) {
            if (Strings.isBlank(user.getName()) || Strings.isBlank(user.getPassword()))
                return "用户名/密码不能为空";
        } else {
            if (Strings.isBlank(user.getPassword()))
                return "密码不能为空";
        }
        String passwd = user.getPassword().trim();
        if (6 > passwd.length() || passwd.length() > 12) {
            return "密码长度错误";
        }
        user.setPassword(passwd);
        if (create) {
            int count = dao.count(User.class, Cnd.where("name", "=", user.getName()));
            if (count != 0) {
                return "用户名已经存在";
            }
        } else {
            if (user.getId() < 1) {
                return "用户Id非法";
            }
        }
        if (user.getName() != null)
            user.setName(user.getName().trim());
        return null;
    }

    @At("/add")
    @RequiresUser
    public Object add(@Param("..") User user) {
        NutMap re = new NutMap();
        String msg = checkUser(user, true);
        if (msg != null) {
            return re.setv("ok", false).setv("msg", msg);
        }
        user = userService.add(user.getName(), user.getPassword());
        return re.setv("ok", true).setv("data", user);
    }


    //需要传入id
    @At("/update")
    @RequiresUser
    public Object update(@Param("password") String password, @Attr("me") int me) {
        if (Strings.isBlank(password) || password.length() < 6)
            return new NutMap().setv("ok", false).setv("msg", "密码不符合要求");
        userService.updatePassword(me, password);
        return new NutMap().setv("ok", true);
    }


    //留意一下,其中的@Attr是取Session/Request中的me属性.
    @At("/delete")
    //多个数据库操作,这里加上了事务
    @Aop(TransAop.READ_COMMITTED)
    @RequiresUser
    public Object delete(@Param("id") int id, @Attr("me") int me) {
        if (me == id) {
            return new NutMap().setv("ok", false).setv("conf/msg", "不能删除当前用户!!");
        }
        dao.delete(User.class, id); // 再严谨一些的话,需要判断是否为>0
        dao.clear(UserProfile.class, Cnd.where("userId", "=", me));
        return new NutMap().setv("ok", true);
    }

    @At("/query")
    @RequiresUser
    public Object query(@Param("name") String name, @Param("..") Pager pager) {
        Cnd cnd = Strings.isBlank(name) ? null : Cnd.where("name", "like", "%" + name + "%");
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(User.class, cnd, pager));
        pager.setRecordCount(dao.count(User.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
    }

    @At("/")
//    @Ok("jsp:jsp.user.list") // 真实路径是 /WEB-INF/jsp/user/list.jsp
    @Ok("jsp:/list")
    @RequiresUser
    public void index() {
    }

    @GET
    @At("/login")
    @Filters
    @Ok("jsp:/login")
    public void loginPage() {
    }

}

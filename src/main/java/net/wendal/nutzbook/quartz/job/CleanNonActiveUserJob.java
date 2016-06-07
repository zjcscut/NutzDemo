package net.wendal.nutzbook.quartz.job;

import net.wendal.nutzbook.bean.User;
import net.wendal.nutzbook.bean.UserProfile;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author zhangjinci
 * @version 2016/6/7 14:52
 */
@IocBean
public class CleanNonActiveUserJob implements Job {

    private static final Log log = Logs.get();

    @Inject
    private Dao dao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("clean Non-Active User , Start");
        // 一天, 测试的时候可以改成1小时之类的,测试为5秒钟
        Date deadtime = new Date(System.currentTimeMillis() - 5 * 1000L);
        //id大于1的全部用户
        Cnd cnd = Cnd.where("userId", ">", 1).and("createTime", "<", deadtime).and(Cnd.exps("emailChecked", "=", false).or("email", "IS", null));
        int deleted = dao.clear(UserProfile.class, cnd);
        log.debugf("delete %d UserProfile", deleted);

        Sql sql = Sqls.create("delete from $user_table where id > 1 and not exists (select 1 from $user_profile_table where $user_table.id = uid ) and ct < @deadtime");
        sql.vars().set("user_table", dao.getEntity(User.class).getTableName());
        sql.vars().set("user_profile_table", dao.getEntity(UserProfile.class).getTableName());
        sql.params().set("deadtime", deadtime);
        dao.execute(sql);
        log.debugf("delete %d User", sql.getUpdateCount());

        log.debug("clean Non-Active User , Done");
    }
}

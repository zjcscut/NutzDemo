package net.wendal.nutzbook.common;

import net.wendal.nutzbook.util.PageModel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.MappingField;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjinci
 * @version 2016/6/13 16:38
 */
@IocBean
public class BaseDao {
    @Inject
    protected Dao dao;

    //留个入口做测试,可以手动设置Dao
    public void setDao(Dao contextDao) {
        if (dao == null) {
            dao = contextDao;
        }
    }


    public Dao getDao() {
        return dao;
    }

    public Sql createSql(String key) {
        Sql sql = Sqls.create(key);
        // 默认设置回调数据集为records
        sql.setCallback(Sqls.callback.records());
        return sql;
    }

    public void create(Class<?> clazz) {
        dao.create(clazz, false);  //如果存在，则不新建表
    }

    public void drop(Class<?> clazz) {
        dao.drop(clazz);
    }

    public <T> T insert(T t) {
        return dao.insert(t);
    }

    public <T> Collection<T> insert(Collection<T> clazz) {
        return dao.insert(clazz);
    }


    public <T> boolean deleteById(Integer id, Class<T> clazz) {
        return dao.delete(clazz, id) == 1;
    }

    public Boolean deleteAll(Class<?> clazz) {
        return dao.clear(clazz) > 0;
    }

    public Boolean deleteByCondition(Class<?> clazz, Condition condition) {
        return dao.clear(clazz, condition) > 0;
    }

    public int deleteByIds(Class<?> clazz, String field, int[] ids) {
        Criteria criteria = Cnd.cri();
        criteria.where().andInIntArray(field, ids);
        return dao.clear(clazz, criteria);
    }

    public int deleteByIds(Class<?> clazz, String field, String val) {
        Criteria criteria = Cnd.cri();
        criteria.where().andIn(field,val.split(","));
        return dao.clear(clazz, criteria);
    }

    public <T> Boolean update(Class<?> clazz) {
        return dao.update(clazz) > 0;
    }

    public <T> Boolean update(Collection<?> collection) {
        return dao.update(collection) > 0;
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return dao.query(clazz, null);
    }

    public <T> T findById(Integer id, Class<T> clazz) {
        return dao.fetch(clazz, id);
    }

    public <T> List<T> findByIds(Class<T> clazz, int[] ids) {
        if (ids != null) {
            Criteria criteria = Cnd.cri();
            MappingField id = dao.getEntity(clazz).getIdField();
            if (id != null) {
                criteria.where().andInIntArray(id.getName(), ids);
            }
            return dao.query(clazz, criteria, null);

        }
        return findAll(clazz);
    }

    public <T> List<T> findByIds(Class<T> clazz, long[] ids) {
        if (ids != null) {
            Criteria criteria = Cnd.cri();
            MappingField id = dao.getEntity(clazz).getIdField();
            if (id != null) {
                criteria.where().andIn(id.getName(), ids);
            }
            return dao.query(clazz, criteria, null);

        }
        return findAll(clazz);
    }


    public PageModel<?> queryByPage(Class<?> clazz, Map<String, String> params) {
        return new PageModel<>();
    }

}

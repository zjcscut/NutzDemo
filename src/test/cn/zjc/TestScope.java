package cn.zjc;

import net.wendal.nutzbook.bean.Person;
import net.wendal.nutzbook.bean.User;
import net.wendal.nutzbook.common.BaseDao;
import org.junit.Test;
import org.nutz.dao.*;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.dao.util.DaoUp;
import org.nutz.dao.util.Daos;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.json.Json;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @author zhangjinci
 * @version 2016/6/13 9:59
 */
public class TestScope {

//    @Inject
//    private BaseDao baseDao;

    @Test
    public void Test1() {
        Ioc ioc = new NutIoc(new JsonLoader("conf/ioc/dao.js"));
        Dao dao = ioc.get(Dao.class);
        //插入一条记录，如果表不存在，创建一张新的表，如果表存在则忽略
//        dao.create(Person.class, false);
//        Person person = new Person();
//        person.setName("zjc");
//        person.setAge(23);
//        dao.insert(person);

//        Person person = dao.fetch(Person.class, 1);
//        System.out.println(person.getName());
//        person.setName("zjcscut");
//        dao.update(person);

        //Chain为链式的set操作，下面的语句转化为SQL：
        // UPDATE t_person SET dead=?   WHERE age>?
//        dao.update(Person.class, Chain.make("dead", true), Cnd.where("age", ">", 150));

        //根据id删除
//        dao.delete(Person.class,2);

//        List<Person> list = dao.query(Person.class, null);
//        System.out.println(list.size());

        //Cnd拼写查询条件
//        List<Person> list = dao.query(Person.class, Cnd.where("name", "like", "z"));
//        System.out.println(list.size());


        //分页查询
        //SELECT * FROM t_person  WHERE age>18 LIMIT 0, 10
//        List<Person> listPage = dao.query(Person.class, Cnd.where("age", ">", 18), dao.createPager(1, 10));
//        System.out.println(Json.toJson(listPage));

        //硬编码
        //SELECT * FROM t_person  WHERE name like '%z%' and age > 20 ORDER BY age ASC
//        List<Person> crowd = dao.query(Person.class, Cnd.wrap("name like '%z%' and age > 20 order by age asc"), null);
//        System.out.println(Json.toJson(crowd));

        //工具类Cnd
        //Condition c = Cnd.where("age",">",30).and("name", "LIKE", "%K%").asc("name").desc("id");
        //这个条件将生成 SQL:WHERE age>30 AND name LIKE '%K%' ORDERBY name ASC, id DESC


        //嵌套表达式
        //SELECT * FROM t_person  WHERE (name LIKE '%z%' AND age>20) OR (name LIKE '%c%' AND age<20) ORDER BY id ASC
//        SqlExpressionGroup s1 = Cnd.exps("name", "LIKE", "%z%").and("age", ">", 20);
//        SqlExpressionGroup s2 = Cnd.exps("name", "LIKE", "%c%").and("age", "<", 20);
//        Condition c = Cnd.where(s1).or(s2).asc("id");
//        List<Person> list = dao.query(Person.class, c);
//        System.out.println(Json.toJson(list));

        //Criteria接口，拼装更复杂的SQL
        //SELECT * FROM t_person  WHERE id IN (1,2,3) AND name NOT IN ('zjc','zzz') AND LOWER(name) LIKE LOWER('%zjcscut%') ORDER BY id ASC
        //LT : 小于 (LessThan)
//        GTE : 大于等于 (GreatThanEqual)
//        LTE : 小于等于 (LessThanEqual)
//        Criteria criteria = Cnd.cri();
//        criteria.where().andIn("id", 1, 2, 3)
//                .andNotIn("name", "zjc", "zzz");
//        if (true) {
//            criteria.where().andLike("name", "zjcscut");
//        }
//        criteria.getOrderBy().asc("id");
//        List<Person> list = dao.query(Person.class, criteria);
//        System.out.println(Json.toJson(list));


        //分页查询
        //Sql:SELECT * FROM t_person  LIMIT 0, 1
        //        QueryResult result = new QueryResult();
//        int pageNum = 1;
//        int pageSize = 1;
//        Pager pager = dao.createPager(pageNum, pageSize);
//        List<Person> list = dao.query(Person.class, null, pager);
//        //不会自动计算结果集的总数，因为涉及到效率问题
//        pager.setRecordCount(dao.count(Person.class));
//        System.out.println(Json.toJson(new QueryResult(list, pager)));


        //自定义SQL
        //(1)硬编码
//        Sql sql = Sqls.create("Delete from t_person where name = 'zjc'");

        //(2)占位符使用
        //$:变量占位符，值直接注入; @:参数占位符，值会转化为"?"，用于创建 PreparedStatement
//        Sql sql = Sqls.create("Delete from $table where name = @name");
//        sql.vars().set("table", "t_person"); // 设置表名
//        sql.params().set("name", "zjc");
//        dao.execute(sql);

//        Sql sql = Sqls.create("INSERT INTO $table ($name,$age,$weight) VALUES(@name,@age,@weight)");
// 为变量占位符设值
//        sql.vars().set("table", "t_person");
//        sql.vars().set("name", "f_name").set("age", "f_age").set("weight", "f_weight");
// 为参数占位符设值
//        sql.params().set("name", "Peter").set("age", 18).set("weight", 60);


        //SELECT需要回调
//        Sql sql = Sqls.create("select name from t_person where id = @id");
//        sql.params().set("id", 1);
//        sql.setCallback(
//                new SqlCallback() {
//                    @Override
//                    public Object invoke(Connection connection, ResultSet resultSet, Sql sql) throws SQLException {
//                        List<String> list = new LinkedList<>();
//                        while (resultSet.next()) {
//                            list.add(resultSet.getString("name"));
//                        }
//                        return list;
//                    }
//                }
//        );
//        dao.execute(sql);
//        List<String> stringList = sql.getList(String.class);
//        System.out.println(stringList);


        //SELECT p.* FROM t_person p JOIN t_user u where u.id = p.id and u.name = 'admin'
        //回调并且给实体赋值
//        Sql sql = Sqls.create("SELECT p.* FROM t_person p JOIN t_user u where u.id = p.id and u.name = 'admin'");
//        sql.setCallback(Sqls.callback.entities());
//        sql.setEntity(dao.getEntity(Person.class));
//        dao.execute(sql);
//        List<Person> list = sql.getList(Person.class);
//        System.out.println(Json.toJson(list));


        //SQL格式文件，放在.sql的文件里面，注解作为键值直接读取
//        ((NutDao) dao).setSqlManager(new FileSqlManager("conf/sqls/all.sqls"));
//        Sql sql = dao.sqls().create("sql2");
//        sql.params().set("id", 1);
//        sql.setCallback(Sqls.callback.entities());
//        Entity<Person> entity = dao.getEntity(Person.class);
//        sql.setEntity(entity);
//        dao.execute(sql);
//        List<Person> list = sql
//                .getList(Person.class);
//        System.out.println(Json.toJson(list));

        //字段过滤
//        Person person = dao.fetch(Person.class, 1);
//        //只保留id和name
////        dao = Daos.ext(dao, FieldFilter.create(Person.class, "^id|name$", true));
//        //忽略id和name
//        dao = Daos.ext(dao, FieldFilter.locked(Person.class, "^id|name$"));
//        person.setName("zzz");
//        //UPDATE t_person SET age=23  WHERE id=1
//        dao.update(person);

        //动态表名
        //表名是放在ThreadLocal里面
        TableName.set("t_person");
        Person person = dao.fetch(Person.class, Cnd.where("id", "=", 1));
        System.out.println(Json.toJson(person));
        Object old = TableName.get();
        try {
            TableName.set("t_user");
            User u = dao.fetch(User.class, Cnd.where("name", "=", "admin"));
            System.out.println(Json.toJson(u));
        } finally {
            TableName.set(old);
        }
        Person person1 = dao.fetch(Person.class, Cnd.where("id", "=", 2));
        System.out.println(Json.toJson(person1));
    }

    @Test
    public void Test2() {
        Ioc ioc = new NutIoc(new JsonLoader("conf/ioc/dao.js"));
        BaseDao baseDao = ioc.get(BaseDao.class, "baseDao");
        Dao dao = ioc.get(Dao.class);
        baseDao.setDao(dao);
        List<Person> list = new ArrayList<>();
        Person person1 = new Person();
        person1.setName("zjc2");
        person1.setAge(23);
        list.add(person1);
        Person person2 = new Person();
        person2.setName("zjc3");
        person2.setAge(24);
        list.add(person2);
        System.out.println(Json.toJson(baseDao.insert(list)));
    }


    @Test
    public void Test3() {
        Ioc ioc = new NutIoc(new JsonLoader("conf/ioc/dao.js"));
        BaseDao baseDao = ioc.get(BaseDao.class, "baseDao");
        Dao dao = ioc.get(Dao.class);
        baseDao.setDao(dao);
        System.out.println(Json.toJson( baseDao.findByIds(Person.class, new int[]{1, 2, 3})));
    }
}

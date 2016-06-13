/**
 * 
 */
package net.wendal.nutzbook.service;


import net.wendal.nutzbook.common.BasicDao;
import net.wendal.nutzbook.common.SystemConst;
import net.wendal.nutzbook.util.PageModel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.OrderBy;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 公共的Service基类
 * 
 * @author Shach
 * @email shacaihong@vip.qq.com
 * 
 */
/*@IocBean*/
public class BaseService<T> {

	@Inject
	protected BasicDao basicDao;

	protected Class<T> entityClass;

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	public BaseService() {
		Type type = getClass().getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) { // 如果Service类被包装代理类的话
			type = getClass().getSuperclass().getGenericSuperclass();
		}
		Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
		this.entityClass = (Class<T>) trueType;
	}

	/**
	 * 根据Id删除数据
	 * 
	 * @param id
	 *            持久化Id
	 * @return true 成功删除一条数据,false删除失败
	 */
	public boolean delById(int id) {
		return basicDao.delById(id, entityClass);
	}

	/**
	 * 根据ID查询一个对象
	 * 
	 * @param id
	 *            持久化Id
	 * @return 查询到的对象
	 */
	public T find(int id) {
		return basicDao.find(id, entityClass);
	}

	/**
	 * 查询数据库中的全部数据
	 * 
	 * @param orderby
	 *            desc 排序的条件
	 * @return List
	 */
	public List<T> search(String orderby) {
		return basicDao.search(entityClass, orderby);
	}

	/**
	 * 根据条件查询数据库中满足条件的数据
	 * 
	 * @param <T>
	 * @param condition
	 * @return
	 */
	public List<T> search(Condition condition) {
		return basicDao.search(entityClass, condition);
	}

	/**
	 * 分页查询表中所有数据
	 * 
	 * 查询的表
	 * 
	 * @param currentPage
	 *            当前页数
	 * @param pageSize
	 *            每页显示数量
	 * @param orderby
	 *            desc排序的条件
	 * @return List
	 */
	public List<T> searchByPage(int currentPage, int pageSize, String orderby, String orderType) {
		if (0 == currentPage) {
			currentPage = 1;
		}
		if (0 == pageSize) {
			pageSize = SystemConst.PAGE_SIZE;
		}
		return basicDao.searchByPage(entityClass, currentPage, pageSize, orderby, orderType);
	}

	/**
	 * 分页带条件查询所有数据
	 * 
	 * @param condition
	 *            查询条件,用Cnd的静态方法构造
	 * @param currentPage
	 *            当前页码
	 * @param pageSize
	 *            每页显示的数据量
	 * @return List
	 */
	public List<T> searchByPage(Condition condition, int currentPage, int pageSize) {
		if (0 == currentPage) {
			currentPage = 1;
		}
		if (0 == pageSize) {
			pageSize = SystemConst.PAGE_SIZE;
		}
		return basicDao.searchByPage(entityClass, condition, currentPage, pageSize);
	}

	/**
	 * 修改一条数据
	 * 
	 * @param t
	 *            修改数据库中的数据
	 * @return true 修改成功,false 修改失败
	 */
	public boolean update(T t) {
		return basicDao.update(t);
	}

	public boolean update(T... t) {
		return basicDao.update(t);
	}

	/**
	 * 修改一条数据
	 * 
	 * @param t
	 *            修改数据库中的数据
	 * @return true 修改成功,false 修改失败
	 */
	public boolean updateNotIgnoreNull(T t) {
		return basicDao.updateNotIgnoreNull(t);
	}

	/**
	 * 根据条件修改指定数据
	 * 
	 * @param chain
	 *            修改的内容
	 * @param condition
	 *            选择条件
	 * @return true 成功,false失败
	 */
	public <T> boolean update(Chain chain, Condition condition) {
		return basicDao.update(entityClass, chain, condition);
	}

	/**
	 * 增加一条数据
	 * 
	 * @param t
	 * @return 返回增加到数据库的这条数据
	 */
	public T save(T t) {
		return basicDao.save(t);
	}

	/**
	 * 批量增加一组数据
	 * 
	 * @param t
	 * @return 返回增加到数据库的这条数据
	 */
	public T[] save(T... t) {
		return basicDao.save(t);
	}

	/**
	 * 批量增加一组数据
	 * 
	 * @param t
	 * @return 返回增加到数据库的这条数据
	 */
	public T[] batchSave(T... t) {
		if (t == null || t.length <= 0)
			return null;
		basicDao.getDao().fastInsert(t);
		String idColumnName = getIdColumnName();
		T[] array = basicDao.search(entityClass,
				Cnd.wrap("order by " + idColumnName + " desc limit 0," + t.length)).toArray(t);
		ArrayUtils.reverse(array);
		return array;
	}

	// 获取主键名称
	private String getIdColumnName() {
		String idColName = "";
		for (Field field : entityClass.getDeclaredFields()) {
			if (field.getAnnotation(Id.class) != null) {
				idColName = field.getAnnotation(Column.class).value();
			}
		}
		return idColName;
	}

	/**
	 * 查询数据库中的数据条数
	 * 
	 * @return int
	 */
	public int searchCount() {
		return basicDao.searchCount(entityClass);
	}

	/**
	 * 根据条件查询数据库中的数据条数
	 * 
	 * @param condition
	 *            条件,用Cnd的静态方法构造
	 * @return int
	 */
	public <T> int searchCount(Condition condition) {
		return basicDao.searchCount(entityClass, condition);
	}

	/**
	 * 根据多个id 查询数据
	 * 
	 * @param ids
	 *            要查询的id,多个用","（逗号）分隔
	 * @return List
	 */
	public List<T> searchByIds(String ids, String orderby) {
		return basicDao.searchByIds(entityClass, ids, orderby);
	}

	/**
	 * 根据多个id 查询数据
	 * 
	 * @param ids
	 *            整形的id数组
	 * @return List
	 */
	public List<T> searchByIds(int[] ids, String orderby) {
		return basicDao.searchByIds(entityClass, ids, orderby);
	}

	/**
	 * 根据多个id删除数据
	 * 
	 * @param c
	 *            要操作的表信息
	 * @param ids
	 *            要删除的id,多个用","（逗号）分隔
	 * @return true 成功,false 失败
	 */
	public void deleteByIds(String ids) {
		basicDao.deleteByIds(entityClass, ids);
	}

	/**
	 * 根据条件返回一个条件
	 * 
	 * @param condition
	 *            查询条件用Cnd构造
	 * @return T
	 */
	public T findByCondition(Condition condition) {
		return basicDao.findByCondition(entityClass, condition);
	}

	/**
	 * 模糊分页查询数据
	 * 
	 * @param value
	 *            查询的字段
	 * @param orderby
	 *            排序条件
	 * @param currentPage
	 *            当前页面
	 * @param pageSize
	 *            页面大小
	 * @return List
	 */
	public List<T> searchPageByLike(String value, String orderby, int currentPage, int pageSize) {

		return basicDao.searchPageByLike(entityClass, value, orderby, currentPage, pageSize);
	}

	/**
	 * 模糊分页查询数据记录总数
	 * 
	 * @param value
	 *            查询的字段
	 * @return List
	 */
	public int searchPageByLike(String value) {

		return basicDao.searchPageByLike(entityClass, value);
	}

	/**
	 * 根据指定的字段模糊分页查询数据
	 * 
	 * @param fieldName
	 *            字段名称
	 * @param value
	 *            模糊条件
	 * @param currentPage
	 *            当前页码
	 * @param pageSize
	 *            每页数据量
	 * @return List
	 */
	public List<T> searchByPageLike(String fieldName, String value, int currentPage, int pageSize) {

		return basicDao.searchByPageLike(entityClass, fieldName, value, currentPage, pageSize);

	}

	/**
	 * 根据某个条件分页查询数据
	 * 
	 * @param fieldName
	 *            匹配字段名
	 * @param value
	 *            匹配的值
	 * @param currentPage
	 *            当前页码
	 * @param pageSize
	 *            每页数据量
	 * @return List
	 */
	public List<T> searchByPage(String fieldName, String value, int currentPage, int pageSize) {

		return basicDao.searchByPage(entityClass, fieldName, value, currentPage, pageSize);
	}

	/**
	 * 根据指定条件返回一个对象
	 * 
	 * @param fieldName
	 *            匹配名称
	 * @param value
	 *            匹配值
	 * @return T
	 */
	public T findByCondition(String fieldName, String value) {
		return basicDao.findByCondition(entityClass, fieldName, value);
	}

	/**
	 * 添加一条数据到数据库中， 该数据包括关联的多个其他数据
	 * 
	 * @param t
	 *            插入数据库的对象
	 * @param fieldName
	 *            关联数据的字段名，一般为List对象
	 * @return T
	 */
	public T saveWidth(T t, String fieldName) {
		return basicDao.saveWidth(t, fieldName);

	}

	/**
	 * 获取关联对象
	 * 
	 * @param t
	 *            查询的对象
	 * @param fieldName
	 *            关联的对象
	 * @return T
	 */
	public T findLink(T t, String fieldName) {
		return basicDao.findLink(t, fieldName);
	}

	/**
	 * 获取关联对象
	 * 
	 * @param ts
	 *            查询的结果集合
	 * @param fields
	 *            关联的对象名称集合
	 * @return
	 */
	public List<T> findLink(List<T> ts, String... fields) {
		for (T t : ts) {
			for (String field : fields) {
				findLink(t, field);
			}
		}

		return ts;
	}

	/**
	 * 更新自身和关联的对象
	 * 
	 * @param t
	 *            修改的对象
	 * @param fieldName
	 *            关联对象
	 * @return T
	 */
	public T updateWith(T t, String fieldName) {
		return basicDao.updateWidth(t, fieldName);
	}

	/**
	 * 仅修改关联的对象的数据
	 * 
	 * @param t
	 *            查询条件
	 * @param fieldName
	 *            修改的对象
	 * @return T
	 */
	public T updateLink(T t, String fieldName) {
		return basicDao.updateLink(t, fieldName);
	}

	/**
	 * 删除自身和关联对象
	 * 
	 * @param t
	 *            删除的对象
	 * @param fieldName
	 *            关联的对象
	 */
	public void deleteWith(T t, String fieldName) {
		basicDao.deleteWidth(t, fieldName);
	}

	/**
	 * 删除关联的对象，不删除自身
	 * 
	 * @param t
	 *            删除的条件
	 * @param fieldName
	 *            删除的关联对象
	 */
	public void deleteLink(T t, String fieldName) {
		basicDao.deleteLink(t, fieldName);
	}

	/**
	 * 保存对象的多对多 关系
	 * 
	 * @param t
	 * @param fieldName
	 */
	public T saveRelation(T t, String fieldName) {
		return basicDao.saveRelation(t, fieldName);
	}

	/**
	 * 保存对象的关联关系,不保存对象本身
	 * 
	 * @param t
	 * @param fieldName
	 * @return
	 */
	public T saveLink(T t, String fieldName) {
		return basicDao.saveLink(t, fieldName);
	}

	/**
	 * 更新对象的多对多关系
	 * 
	 * @param fieldName
	 *            更新的字段名称
	 * @param chain
	 *            更新的内容
	 * @param condition
	 *            更新的条件
	 * @return true 成功,false 失败
	 */
	public boolean updateRelation(String fieldName, Chain chain, Condition condition) {

		return basicDao.updateRelation(entityClass, fieldName, chain, condition);
	}

	/**
	 * 对于 '@One' 和 '@Many'，对应的记录将会删除 而 '@ManyMay' 对应的字段，只会清除关联表中的记录
	 * 
	 * @param t
	 * @param fieldName
	 * @return
	 */
	public T clearRelation(T t, String fieldName) {
		return basicDao.clearRelation(t, fieldName);
	}

	/**
	 * 根据中间表分页查询数据
	 * 
	 * @param joinTabel
	 *            中间表
	 * @param cloumnName
	 *            要获取中间表的字段
	 * @param condition
	 *            查询条件
	 * @param group
	 *            主查询条件组
	 * @param orderby
	 *            排序方式
	 * @param currentPage
	 *            当前页面
	 * @param pageSize
	 *            每页显示数据
	 * @return
	 */
	public List<T> searchByRelation(String joinTabel, String cloumnName, Condition condition,
									SqlExpressionGroup group, String orderby, int currentPage, int pageSize) {

		return basicDao.searchByRelation(entityClass, joinTabel, cloumnName, condition, group,
				orderby, currentPage, pageSize);
	}

	/**
	 * 查询数据的总数
	 * 
	 * @param joinTabel
	 *            中间表
	 * @param cloumnName
	 *            要获取中间表的字段
	 * @param condition
	 *            查询条件
	 * @param group
	 *            主查询条件组
	 * @param orderby
	 *            排序方式
	 * @return
	 */
	public <T> int searchCount(String joinTabel, String cloumnName, Condition condition,
							   SqlExpressionGroup group, String orderby) {

		return basicDao.searchCount(entityClass, joinTabel, cloumnName, condition, group, orderby);
	}

	/**
	 * @Description: 获得总记录条数
	 * @return int
	 * @author zdg
	 * @throws
	 */
	public int searchCountBySql(String sql) {
		List<Record> counts = basicDao.searchBySql(sql);
		if (counts.isEmpty()) {
			return 0;
		}
		return Integer.valueOf(counts.get(0).getString("counts"));
	}
	

    
    /**
     * 获取总记录数
     * @param sql
     * @return
     */
    public int searchCountBySql(Sql sql) {
        return searchCountBySql( String.format( "select count(*) counts from (%s) tmp", sql.toString() ) );
    }

	/**
	 * @Description: sql语句 多表查询分页
	 * @return List<Record>
	 * @author eddy
	 * @throws
	 */
	public List<Record> searchBySql(String sqlstr) {
		return basicDao.searchBySql(sqlstr);
	}
	
	/**
	 * 从sqls文件中创建Sql对象,默认回到为records，如果需要自定义，需要重新设置 callback
	 */
	public Sql createSql(String key){
		Sql sql = basicDao.getDao().sqls().create(key);
		// 默认设置回调数据集为records
		sql.setCallback(Sqls.callback.records());
		return sql;
	}
	
	/**
	 * 创建分页查询对象
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Pager createPager(int pageNumber, int pageSize){
		if (0 == pageNumber) {
            pageNumber = 1;
        }
        if (0 == pageSize) {
            pageSize = SystemConst.PAGE_SIZE;
        }
        return basicDao.getDao().createPager(pageNumber, pageSize);
	}
	
	/**
	 * 设置排序条件
	 * @param params 参数集
	 * @param cnd 条件对象
	 * @return 排序对象
	 */
	public OrderBy setOrderBy(Map<String, String> params, Cnd cnd) {
	   return setOrderBy(params, cnd, null);
	}
	
	/**
	 * 设置排序条件
	 * @param params 参数集
	 * @param cnd 条件对象
	 * @param defaultColume 指定缺省排序字段
	 * @return 排序对象
	 */
	public OrderBy setOrderBy(Map<String,String> params, Cnd cnd, String defaultColume){
		return setOrderBy(params, cnd, defaultColume, null);
	}
	
	/**
     * 设置排序条件
     * @param params 参数集
     * @param cnd 条件对象
     * @param defaultColume 指定缺省排序字段
     * @param defaultOrderDirection 指定缺省排序方向
     * @return 排序对象
     */
    public OrderBy setOrderBy(Map<String,String> params, Cnd cnd, String defaultColume, String defaultOrderDirection) {
        String idName = null;
        if(StringUtils.isBlank(defaultColume)){
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                if ( field.isAnnotationPresent( Id.class ) ) {
                    Column column = field.getAnnotation( Column.class );
                    idName = column.value();
                    break;
                }
            }
            
            if (StringUtils.isBlank( idName )) {
                throw new ServiceException( "找不到实体对象的ID字段." );
            }
        }else{
            idName = defaultColume;
        }
        
        // 处理排序
        String orderfield = params.get("orderField");
        String orderDirection = params.get("orderDirection");
        if(StringUtils.isEmpty(orderDirection)){
        	orderDirection = defaultOrderDirection;
        }
        
        return cnd.orderBy( 
                StringUtils.defaultIfBlank(orderfield, idName), 
                StringUtils.defaultIfBlank(orderDirection, "DESC" ) 
            );
    }
    
    /**
     * 查询分页数据
     * @param params 查询参数
     * @param builder 查询条件builder
     * @param sqlKey SQL的key
     * @param pageNumber 当前页面
     * @param pageSize 分页大小
     * @return
     */
    public PageModel<Record> queryPageByParams(Map<String, String> params, CndBuilder builder, String sqlKey, int pageNumber, int pageSize){
    	// 组装查询条件
    	Cnd cnd = Cnd.NEW();
    	if(null != builder){
    		cnd = builder.build(params);
    	}

    	Sql sql = createSql(sqlKey);
        // 设置动态查询条件
        sql.setCondition(cnd);
        
        int recordCount = searchCountBySql( sql );
        
        // 开启分页设置
        sql.setPager(createPager(pageNumber, pageSize));
        
        // 查询结果集
        basicDao.getDao().execute(sql);
        List<Record> list = sql.getList(Record.class);
        
        return new PageModel<Record>(list, recordCount, pageNumber, pageSize);
    }
    
    /**
     * 查询分页数据，该接口只需要传递Cnd对象即可，默认查询对应实体类的表，不支持跨表查询
     * 跨表查询请使用 {@see com.whtr.eam.common.service.BaseService.queryPageByParams(Map<String, String>, CndBuilder, String, int, int)}
     * @param params 查询参数
     * @param builder 查询条件builder
     * @param pageNumber 当前页面
     * @param pageSize 分页大小
     * @return
     */
    public PageModel<T> queryPageByParams(Map<String, String> params, CndBuilder builder, int pageNumber, int pageSize){
    	// 组装查询条件
    	Cnd cnd = Cnd.NEW();
    	if(null != builder){
    		cnd = builder.build(params);
    	}
        int recordCount = searchCount(cnd);
        // 查询结果集
        List<T> list = basicDao.getDao().query(entityClass, cnd, createPager(pageNumber, pageSize));
        return new PageModel<T>(list, recordCount, pageNumber, pageSize);
    }
    
    /**
     * 
     * @param params
     * @return
     */
    public List<Record> queryByParams(Map<String, String> params, CndBuilder builder, String sqlKey){
    	Cnd cnd = Cnd.NEW();
    	if(null != builder){
    		cnd = builder.build(params);
    	}

    	Sql sql = createSql(sqlKey);
        // 设置动态查询条件
        sql.setCondition(cnd);
        
        // 查询结果集
        basicDao.getDao().execute(sql);
        return sql.getList(Record.class);
    } 
    
//    /**
//     * 保存日志
//     *
//     * @param strType
//     * @param strDesc
//     * @param sess
//     * @param req
//     */
//    protected void saveLog(String strType, String strDesc, HttpSession sess,
//            HttpServletRequest req) {
//        try {
//            Log _log = new Log();
//
//            String uName = "未知";
//            Integer uId = null;
//            User user = (User) sess.getAttribute("user");
//            if (user != null) {
//                 uName = user.getUsername();
//                 uId = user.getUserId();
//            }
//            if (StringUtils.isBlank(strType)) {
//                strType = "操作日志";
//            }
//
//            _log.setLogtype(strType);
//            _log.setOperman(uName);
//            _log.setLogdesc(strDesc);
//            _log.setLogdate(new Timestamp(System.currentTimeMillis()));
//            _log.setIp(NetworkUtil.getIpAddress(req));
//            _log.setCreaterid(uId);
//            _log.setCreatetime(new Timestamp(System.currentTimeMillis()));
//
//            logService.save(_log);
//        } catch (Exception e) {
//            log.error("系统操作日志写入出错.", e);
//        }
//    }
//
//	/**
//	 * 保存日志
//	 *
//	 * @param strType
//	 * @param strDesc
//	 * @param user
//	 * @param ip
//	 */
//	protected void saveLog(String strType, String strDesc, User user, String ip) {
//		try {
//			Log _log = new Log();
//
//			String uName = "未知";
//			Integer uId = null;
//			if (user != null) {
//				uName = user.getUsername();
//				uId = user.getUserId();
//			}
//			if (StringUtils.isBlank(strType)) {
//				strType = "操作日志";
//			}
//
//			_log.setLogtype(strType);
//			_log.setOperman(uName);
//			_log.setLogdesc(strDesc);
//			_log.setLogdate(new Timestamp(System.currentTimeMillis()));
//			_log.setIp(ip);
//			_log.setCreaterid(uId);
//			_log.setCreatetime(new Timestamp(System.currentTimeMillis()));
//
//			logService.save(_log);
//		} catch (Exception e) {
//			log.error("系统操作日志写入出错.", e);
//		}
//	}
}

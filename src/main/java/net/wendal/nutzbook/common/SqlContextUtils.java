package net.wendal.nutzbook.common;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.sql.Criteria;

import java.util.Map;


/**
 * @author zhangjinci
 * @version 2016/6/24 10:10
 */
public class SqlContextUtils {


    public static void buildCriteriaByParams(Map<String, String> params,
                                             String paramName,
                                             String field,
                                             String symbol,
                                             Criteria criteria) {
        String paramVal = params.get(paramName);
        if (StringUtils.isNotBlank(paramVal)) {
            paramVal = paramVal.trim();
            if ("LIKE".equalsIgnoreCase(symbol)) {
                buildLikeCriteria(paramVal, field, criteria);
            } else if ("LLIKE".equalsIgnoreCase(symbol)) {
                buildLikeLeftCriteria(paramVal, field, criteria);
            } else if ("RLIKE".equalsIgnoreCase(symbol)) {
                buildLikeRightCriteria(paramVal, field, criteria);
            } else if ("OR".equalsIgnoreCase(symbol)) {
                buildOrCriteria(paramVal, field, criteria);
            } else if ("IN".equalsIgnoreCase(symbol)) {
                buildInCriteria(paramVal, field, criteria);
            } else {
                buildEqualCriteria(paramVal, field, criteria);
            }
        }
    }

    public static void buildEqualCriteria(String paramVal, String field, Criteria criteria) {
        criteria.where().andEquals(field, paramVal);
    }


    public static void buildOrCriteria(String paramVal, String field, Criteria criteria) {
        criteria.where().orEquals(field, paramVal);
    }

    public static void buildInCriteria(String paramVal, String field, Criteria criteria) {
        criteria.where().andIn(field, paramVal.split(","));
    }

    /**
     * '%'+paramValue+'%'
     *
     * @param paramVal
     * @param field
     * @param criteria
     */
    public static void buildLikeCriteria(String paramVal, String field, Criteria criteria) {
        criteria.where().andLike(field, paramVal);
    }

    /**
     * paramValue+'%'
     *
     * @param paramVal
     * @param field
     * @param criteria
     */
    public static void buildLikeLeftCriteria(String paramVal, String field, Criteria criteria) {
        criteria.where().andLikeL(field, paramVal);
    }

    /**
     * '%'+paramValue
     *
     * @param paramVal
     * @param field
     * @param criteria
     */
    public static void buildLikeRightCriteria(String paramVal, String field, Criteria criteria) {
        criteria.where().andLikeR(field, paramVal);
    }
}

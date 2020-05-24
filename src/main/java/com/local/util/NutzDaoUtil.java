package com.local.util;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.dao.util.Daos;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NutzDaoUtil {
    private NutzDaoUtil() {

    }

    /**
     * Nutz 自定义查询工具类
     * @param t 实体类
     * @param sql 查询sql
     * @param classOfT 实体类class
     * @param dao
     * @param <T> 泛型
     * @return
     */
    public static <T> List<T> getQueryBySql(T t,Sql sql,Class<T> classOfT,Dao dao) {
        sql.setCallback(Sqls.callback.entities());
        Entity<T> entity = dao.getEntity(classOfT);
        sql.setEntity(entity);
        dao.execute(sql);
        return sql.getList(classOfT);
    }



    public static <T> QueryResult getQueryBySqlAndPager(T t,Sql sql,Class<T> classOfT,Dao dao,Integer pageNumber, Integer pageSize) {
        sql.setCallback(Sqls.callback.entities());
        Entity<T> entity = dao.getEntity(classOfT);
        sql.setEntity(entity);
        Long count = Daos.queryCount(dao, sql);
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber);
        pager.setPageSize(pageSize);
        sql.setPager(pager);
        dao.execute(sql);
        if (!StrUtils.isBlank(pager)) {
            pager = new Pager();
        }
        pager.setRecordCount(count.intValue());
        QueryResult queryResult = new QueryResult(sql.getList(classOfT), pager);
        return queryResult;
    }

    /**
     *
     * Nutz 自定义查询工具类
     * @param t 实体类
     * @param sql 查询sql
     * @param classOfT 实体类class
     * @param dao
     * @param <T> 泛型
     * @param map 参数集合 city=@city =>  map.put("city", city)
     * @return
     */
    public static <T> List<T> getQueryBySqlParams(T t, Sql sql, Class<T> classOfT, Dao dao, Map<String,Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()){
            sql.params().set(entry.getKey(), entry.getValue());
        }
        sql.setCallback(Sqls.callback.entities());
        Entity<T> entity = dao.getEntity(classOfT);
        sql.setEntity(entity);
        sql.forceExecQuery();
        dao.execute(sql);
        return sql.getList(classOfT);
    }

    /**
     * Nutz 自定义查询工具类
     * @param t 变量类型
     * @param sql 查询sql
     * @param classOfT 变量类型class
     * @param dao
     * @param map 参数集合 city=@city =>  map.put("city", city)
     * @param columnLabel 返回集合 字段名 eg: name id
     * @param <T>
     * @return
     */
    public static <T> List<T> getConstantQueryBySqlParams(T t, Sql sql, Class<T> classOfT, Dao dao, Map<String,Object> map,String columnLabel) {
        for (Map.Entry<String, Object> entry : map.entrySet()){
            sql.params().set(entry.getKey(), entry.getValue());
        }
        sql.setCallback(new SqlCallback() {
            public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
                List<String> list = new LinkedList<String>();
                while (rs.next())
                    list.add(rs.getString(columnLabel));
                return list;
            }
        });
        dao.execute(sql);
        return sql.getList(classOfT);

    }


    /**
     *
     * Nutz 自定义查询工具类
     * @param t 实体类
     * @param sql 查询sql
     * @param classOfT 实体类class
     * @param dao
     * @param <T> 泛型
     * @param map 参数集合 city=@city =>  map.put("city", city)
     * @return
     */
    public static <T> QueryResult getQueryBySqlParamsAndPager(T t, Sql sql, Class<T> classOfT, Dao dao, Map<String,Object> map,Integer pageNumber, Integer pageSize) {
        if (map!=null && map.size()>0){
            for (Map.Entry<String, Object> entry : map.entrySet()){
                sql.params().set(entry.getKey(), entry.getValue());
            }
        }
        sql.setCallback(Sqls.callback.entities());
        Entity<T> entity = dao.getEntity(classOfT);
        sql.setEntity(entity);
        Long count = Daos.queryCount(dao, sql);
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber);
        pager.setPageSize(pageSize);
        sql.setPager(pager);
        dao.execute(sql);
        if (!StrUtils.isBlank(pager)) {
            pager = new Pager();
        }
        pager.setRecordCount(count.intValue());
        QueryResult queryResult = new QueryResult(sql.getList(classOfT), pager);
        return queryResult;
    }

    /**
     * Nutz 自定义动态参数查询工具类
     * @param t
     * @param sql
     * @param classOfT
     * @param dao
     * @param map
     * @param pageNumber
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> QueryResult getQueryBySqlDynamoParamsAndPager(T t, Sql sql, Class<T> classOfT, Dao dao, Map<String,Object> map,Integer pageNumber, Integer pageSize) {
        if (map!=null && map.size()>0){
            for (Map.Entry<String, Object> entry : map.entrySet()){
                sql.params().set(entry.getKey(), entry.getValue());
            }
        }
        SqlExpressionGroup group1= Cnd.exps("1", "=", "1");
        sql.setCallback(Sqls.callback.entities());
        Entity<T> entity = dao.getEntity(classOfT);
        sql.setEntity(entity);
        Long count = Daos.queryCount(dao, sql);
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber);
        pager.setPageSize(pageSize);
        sql.setPager(pager);
        dao.execute(sql);
        if (!StrUtils.isBlank(pager)) {
            pager = new Pager();
        }
        pager.setRecordCount(count.intValue());
        QueryResult queryResult = new QueryResult(sql.getList(classOfT), pager);
        return queryResult;
    }

    /**
     * 获取字符串集合
     * sql.params().set("name", "A%");
     * columnLabel 返回集合单列 eg; "id"
     */
    public static List<String> getStrList(Dao dao, Sql sql, String columnLabel) {
        sql.setCallback(new SqlCallback() {
            public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
                List<String> list = new LinkedList<>();
                while (rs.next())
                    list.add(rs.getString(columnLabel));
                return list;
            }
        });
        dao.execute(sql);
        return sql.getList(String.class);
        // Nutz内置了大量回调, 请查看Sqls.callback的属性
    }

    /**
     *批量更新
     * 将所有的 id 大于 35 的 Pet 对象的 masterId 设置为 45
     */
    public void updateListModel(Dao dao,String Condition){
        Sql sql = Sqls.create("UPDATE t_pet SET masterid=@masterId $condition");
        sql.params().set("masterId", 45);
        sql.setCondition(Cnd.wrap("id>35"));
        dao.execute(sql);
    }
}

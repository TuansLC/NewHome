package vn.hbm.core.repository;

import vn.hbm.core.annotation.AntTable;
import vn.hbm.core.bean.QueryBean;

import java.util.List;

public interface IBaseRepo {
    String getTableName(AntTable antTable);
    <T> QueryBean getInsertQuery(String transId, T obj, boolean withField, String... fields) throws Exception;
    <T> QueryBean getUpdateQuery(String transId, T obj, String whereClause, Object[] params, boolean withField, String... fields) throws Exception;
    <T> QueryBean getUpdateQuery(String transId, T obj, String whereClause, Object[] params, boolean withField, boolean withNull, String... fields) throws Exception;
    <T> QueryBean getDeleteQuery(String transId, T obj, String whereClause, Object[] params) throws Exception;
    <T> QueryBean getRecordById(String transId, T obj, Object key) throws Exception;
    <T> QueryBean getRecordAll(String transId, T obj) throws Exception;
    <T> QueryBean getRecordByParam(String transId, T obj, String whereClause, Object[] params) throws Exception;
    <T> QueryBean getRecordBySql(String transId, String sql, Object[] params) throws Exception;
    <T> int executeUpdate(String transId, String sql, List<Object> params) throws Exception;
    <T> List<T> executeQuery(String transId, String sql, List<Object> params) throws Exception;
    <T> List<T> callProcedure() throws Exception;
}

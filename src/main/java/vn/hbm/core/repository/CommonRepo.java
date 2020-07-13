package vn.hbm.core.repository;

import java.util.List;

public interface CommonRepo {
    boolean isExistTable(String tblName);
    void createTable(String tblName, String coreTableName) throws Exception;
    <T> int create(String transId, T bean);
    <T> int update(String transId, T bean, String whereClause, Object[] params, boolean withField, String... fields);
    <T> int update(String transId, T bean, String whereClause, Object[] params, boolean withField, boolean withNull, String... fields);
    <T> int delete(String transId, T bean, String whereClause, Object[] params);
    <T> List<Object[]> findById(String transId, T bean, Long id);
    <T> List<T> findAll(String transId, T bean);
    <T> List<T> findByClause(String transId, T bean, String whereClause, Object[] params);
    <T> List<T> findBySQL(String transId, String sql, Object[] params) throws Exception;
}

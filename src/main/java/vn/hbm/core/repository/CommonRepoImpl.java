package vn.hbm.core.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.hbm.core.bean.QueryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
@Slf4j
public class CommonRepoImpl extends BaseRepo implements CommonRepo {

    @PersistenceContext
    EntityManager em;

    public boolean isExistTable(String tblName) {
        try {
            Query query = em.createNativeQuery("SELECT 1 FROM " + tblName);
            List lst = query.getResultList();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void createTable(String tblName, String coreTableName) throws Exception {
        try {
            String sql = "CREATE TABLE " + tblName + " LIKE " + coreTableName;
            Query query = em.createNativeQuery(sql);
            query.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public <T> int create(String transId, T bean) {
        int count = -1;
        try {
            QueryBean queryBean = getInsertQuery(transId, bean, false);
            count = executeUpdate(transId, queryBean.getSql(), queryBean.getListValue());
        } catch (Exception e) {
            log.error("", e);
        }

        return count;
    }

    @Override
    public <T> int update(String transId, T bean, String whereClause, Object[] params, boolean withField, String... fields) {
        int count = -1;
        try {
            QueryBean queryBean = getUpdateQuery(transId, bean, whereClause, params, withField, fields);
            count = executeUpdate(transId, queryBean.getSql(), queryBean.getListValue());
        } catch (Exception e) {
            log.error("", e);
        }

        return count;
    }

    @Override
    public <T> int update(String transId, T bean, String whereClause, Object[] params, boolean withField, boolean withNull, String... fields) {
        int count = -1;
        try {
            QueryBean queryBean = getUpdateQuery(transId, bean, whereClause, params, withField, withNull, fields);
            count = executeUpdate(transId, queryBean.getSql(), queryBean.getListValue());
        } catch (Exception e) {
            log.error("", e);
        }

        return count;
    }

    @Override
    public <T> int delete(String transId, T bean, String whereClause, Object[] params) {
        int count = -1;
        try {
            QueryBean queryBean = getDeleteQuery(transId, bean, whereClause, params);
            count = executeUpdate(transId, queryBean.getSql(), queryBean.getListValue());
        } catch (Exception ex) {
            log.error(transId + "::" + ex.getMessage(), ex);
        }

        return count;
    }

    @Override
    public <T> List<Object[]> findById(String transId, T bean, Long id) {
        try {
            QueryBean queryBean = getRecordById(transId, bean, id);
            List<Object[]> lst = executeQuery(transId, queryBean.getSql(), queryBean.getListValue());
            return lst;
        } catch (Exception ex) {
            log.error(transId + "::" + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public <T> List<T> findAll(String transId, T bean) {
        try {
            QueryBean queryBean = getRecordAll(transId, bean);
            List<T> lst = executeQuery(transId, queryBean.getSql(), queryBean.getListValue());
            return lst;
        } catch (Exception ex) {
            log.error(transId + "::" + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public <T> List<T> findByClause(String transId, T bean, String whereClause, Object[] params) {
        try {
            QueryBean queryBean = getRecordByParam(transId, bean, whereClause, params);
            List<T> lst = executeQuery(transId, queryBean.getSql(), queryBean.getListValue());
            return lst;
        } catch (Exception ex) {
            log.error(transId + "::" + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public <T> List<T> findBySQL(String transId, String sql, Object[] params) throws Exception {
        try {
            QueryBean queryBean = getRecordBySql(transId, sql, params);
            List<T> lst = executeQuery(transId, queryBean.getSql(), queryBean.getListValue());
            return lst;
        } catch (Exception ex) {
            log.error(transId + "::" + ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public <T> int executeUpdate(String transId, String sql, List<Object> params) throws Exception {
        long time1 = System.currentTimeMillis();
        int result = -1;
        try {
            Query query = em.createNativeQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i+1, params.get(i));
            }
            result = query.executeUpdate();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            log.debug(transId + "::EXECUTE::TIME=" + (System.currentTimeMillis() - time1) + "|UPDATED=" + result);
        }
    }

    @Override
    public <T> List<T> executeQuery(String transId, String sql, List<Object> params) throws Exception {
        long time1 = System.currentTimeMillis();
        List lstResult = null;
        try {
            Query query = em.createNativeQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i+1, params.get(i));
            }
            lstResult = query.getResultList();
            return lstResult;
        } catch (Exception ex) {
            throw ex;
        } finally {
            log.debug(transId + "::EXECUTE::TIME=" + (System.currentTimeMillis() - time1) + "|RESULT=" + (lstResult==null ? 0 : lstResult.size()));
        }
    }
}

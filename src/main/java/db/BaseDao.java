package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by yuwc on 2017/6/1.
 */
public class BaseDao<T> {
    private SessionFactory sessionFactory = Client.getSessionFactory();

    /**
     * 取得泛型实现类型
     */
    @SuppressWarnings("unchecked")
    protected Class<T> typeClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 查询所有记录
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Session session = null;
        try {
            String queryString = "from " + typeClass().getCanonicalName();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(queryString);
            List<T> pojos = query.list();
            session.getTransaction().commit();
            return pojos;
        } catch (RuntimeException re) {
            throw re;
        } finally {
            if (session != null) {
                if (session.isOpen()) {
                    //关闭session
                    session.close();
                }
            }
        }
    }

    public void save(T pojo) {
        Session session = null;
        try {
            session = Client.getSessionFactory().getCurrentSession();
            //开启事务
            session.beginTransaction();

            session.save(pojo);
            //提交事务
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                if (session.isOpen()) {
                    //关闭session
                    session.close();
                }
            }
        }
    }

    /**
     * 单属性相等查询
     */
    @SuppressWarnings("unchecked")
    public List<T> findByPropertyEqual(String propertyName, String value,
                                       String type) {
        try {
            String queryString = "from " + typeClass().getCanonicalName()
                    + " as model where model." + propertyName + "= ?";
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(queryString);
            if (type.equals("String")) {
                query.setString(0, value.toLowerCase());
            } else if (type.equals("long")) {
                query.setLong(0, Long.parseLong(value));
            } else if (type.equals("int")) {
                query.setInteger(0, Integer.parseInt(value));
            }
            List<T> pojos = query.list();
            session.getTransaction().commit();
            return pojos;
        } catch (RuntimeException re) {
            throw re;
        }
    }


}

package com.example.demo.repo;
import com.example.demo.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {
    private static SessionFactory factory;


    public UserRepo() {
        try {
            factory = new Configuration().
                    configure().
                    addAnnotatedClass(User.class).
                    buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Object findByName(String name)  {
        var session = factory.openSession();
        try {
            var sql = "FROM User where name = :name";
            var query = session.createQuery(sql);
            query.setParameter("name", name);

            var items = query.list();

            return items.size() > 0 ? items.get(0) : null;
        } catch (HibernateException exception) {
            System.err.println(exception);
        }finally {
            session.close();
        }
        return null;
    }


    public Long saveUser(User user)  {
        var session = factory.openSession();
        Long userId = null;
        try {
            userId = (Long)session.save(user);
            return userId;
        } catch (HibernateException exception) {
            System.err.println(exception);
        }finally {
            session.close();
        }
        return null;
    }

}

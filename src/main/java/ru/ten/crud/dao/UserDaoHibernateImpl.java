package ru.ten.crud.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.ten.crud.model.User;

import java.util.List;

public class UserDaoHibernateImpl implements UserDAO<User> {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    @Override
    public void insertDAO(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.getTransaction();
        try {
            tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
        } catch (Exception e) {
            tx1.rollback();
        } finally {
            session.close();

        }
    }

    @Override
    public void updateDAO(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            tx1 = session.beginTransaction();
            session.update(user);
            tx1.commit();
        } catch (Exception e) {
            tx1.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteDAO(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        tx1.commit();
        session.close();
        return false;
    }


    @Override
    public User selectUser(int id) {
        return (User) sessionFactory.openSession().get(User.class, id);
    }

    @Override
    public String getRoleByLoginPassword(String login, String password) {
        User user = (User) sessionFactory.openSession().get(User.class, login);
        return user.getRole();
    }

    @Override
    public User selectUserByLoginPassword(String login, String password) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User u where u.login = :login");//прмиер hql что рабаотет с сущностью USer класс а не с таблицей newuser
        query.setParameter("login", login);
        User user = (User) query.uniqueResult();
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        return (List<User>) sessionFactory.openSession().createQuery("FROM User").list();
    }
}

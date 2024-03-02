package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        SessionFactory sessionFactoryCreateTable = Util.getSessionFactory(true);
        sessionFactoryCreateTable.close();
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactoryDropTable = Util.getSessionFactory(false);
        sessionFactoryDropTable.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            User addUser = new User(name, lastName, age);
            session.persist(addUser);
            transaction.commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (PersistenceException ex) {
            transaction.rollback();
        }
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch (PersistenceException ex) {
            transaction.rollback();
        }
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            allUsers = session.createQuery("from User").getResultList();
            transaction.commit();
        } catch (PersistenceException ex) {
            transaction.rollback();
        }
        session.close();
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (PersistenceException ex) {
            transaction.rollback();
        }
        session.close();
    }

    @Override
    public void closeConnection() {
        sessionFactory.close();
    }

}

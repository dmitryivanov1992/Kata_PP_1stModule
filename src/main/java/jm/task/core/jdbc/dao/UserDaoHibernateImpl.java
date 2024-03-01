package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory hibernateFactory = Util.getHibernateFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = hibernateFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE `mydb`.`users` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT NULL,\n" +
                    "  PRIMARY KEY (`id`));").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = hibernateFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE `mydb`.`users`").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            session.getTransaction().rollback();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = hibernateFactory.getCurrentSession();
        try {
            session.beginTransaction();
            User addUser = new User(name, lastName, age);
            session.persist(addUser);
            session.getTransaction().commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (PersistenceException ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = hibernateFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        Session session = hibernateFactory.getCurrentSession();
        try {
            session.beginTransaction();
            allUsers = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            session.getTransaction().rollback();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = hibernateFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void closeConnection() {
        hibernateFactory.close();
    }

}

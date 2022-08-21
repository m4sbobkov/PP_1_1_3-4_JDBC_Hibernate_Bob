package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sqlQuery = """
                CREATE TABLE IF NOT EXISTS users (
                  id INT NOT NULL AUTO_INCREMENT,
                  name VARCHAR(45) NOT NULL,
                  lastName VARCHAR(45) NOT NULL,
                  age INT NOT NULL,
                  PRIMARY KEY (`id`))""";

        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.createSQLQuery(sqlQuery).addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.createSQLQuery(sqlQuery).addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.delete(session.get(User.class, id));


            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        String sqlQuery = "DELETE FROM kata_db.users";

        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.createSQLQuery(sqlQuery).addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}

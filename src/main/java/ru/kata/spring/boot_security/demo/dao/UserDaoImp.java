package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }
    @Override
    public void createUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public void updateUser(User updateUser) {
        entityManager.merge(updateUser);
        entityManager.flush();
    }

    @Override
    public User readUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(long id) {
        Query query = entityManager.createQuery("delete from User where id = :userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }

    @Override
    public User findUserByName(String firstname) {
        User user = entityManager.createQuery(
                        "SELECT u from User u WHERE u.firstname = :firstname", User.class).
                setParameter("firstname", firstname).getSingleResult();
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = entityManager.createQuery(
                        "SELECT u from User u WHERE u.email = :email", User.class).
                setParameter("email", email).getSingleResult();
        return user;
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }




}

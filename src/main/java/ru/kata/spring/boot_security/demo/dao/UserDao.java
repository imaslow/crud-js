package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    void createUser(User user);
    void updateUser(User updateUser);
    User readUser(long id);
    void deleteUser(long id);
    User findUserByName(String name);
    User findByEmail(String username);
    User findById(long id);

}


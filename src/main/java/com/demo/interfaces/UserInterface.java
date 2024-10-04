package com.demo.interfaces;

import com.demo.entity.User;

import java.util.List;
import java.util.Map;

public interface UserInterface {
    void create(User user);
    void update(User user);
    List<User> display();
    void delete(String email);
    User findUserById(Long id);
    Map<String, Object> findUserByEmail(String email);
}

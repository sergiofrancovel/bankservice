package org.example.dao;

import org.example.models.User;

public interface UserRepository extends Repository<Integer, User> {
    void updateById(Integer integer, String obj);

    void updateById(int id);

    User getByUsername(String username);
}

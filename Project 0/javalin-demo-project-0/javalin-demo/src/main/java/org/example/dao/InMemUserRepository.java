package org.example.dao;

import org.example.models.Roles;
import org.example.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemUserRepository implements UserRepository {
    private Map<Integer, User> users;

    public InMemUserRepository() {
        AtomicInteger idGen = new AtomicInteger(1);
        users = new HashMap<>();
        users.put(idGen.getAndIncrement(), new User(idGen.get(), "august.duet", "p@$$w0rd123", new Roles[]{Roles.ADMIN, Roles.USER}));
        users.put(idGen.getAndIncrement(), new User(idGen.get(), "john.doe", "p@$$w0rd123", new Roles[]{Roles.USER}));
    }

    @Override
    public Integer save(User obj) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(Integer integer) {
        return null;
    }

    @Override
    public void updateById(Integer integer, User obj) {

    }

    @Override
    public void updateById(Integer integer, String obj) {
    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public void deleteById(Integer integer) {

    }
    @Override
    public void updateById(int id) {

    }

    @Override
    public User getByUsername(String username) {
        return users.values()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);
    }
}

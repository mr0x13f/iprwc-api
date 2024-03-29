package com.timw.iprwc.db;

import com.timw.iprwc.models.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll() {
        return super.list((Query) namedQuery(
                "iprwc.User.findAll"));
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable( uniqueResult((Query) namedQuery(
                "iprwc.User.findByEmail")
                .setParameter("email", email)));
    }

    public Optional<User> findById(String userId) {
        return Optional.ofNullable( uniqueResult((Query) namedQuery(
                "iprwc.User.findById")
                .setParameter("userId", userId)));
    }

    public void delete(String userId) {
        namedQuery(
                "iprwc.User.delete")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    public User create(User user) {

        return persist(user);

    }

    public User update(String userId, User user) {

        user.userId = userId;
        return persist(user);

    }


}

package com.epam.news.repository.impl;

import com.epam.news.exception.UserNotFoundException;
import com.epam.news.model.User;
import com.epam.news.repository.UserRepository;
import com.epam.news.util.MessageLocaleService;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    private MessageLocaleService locale;

    @Autowired
    public void setMessageLocaleService(MessageLocaleService locale) {
        this.locale = locale;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "UPDATE User set name = :name, role = :role, login = :login, password = :password, email = :email WHERE id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("name", user.getName());
        query.setParameter("role", user.getRole());
        query.setParameter("login", user.getLogin());
        query.setParameter("password", user.getPassword());
        query.setParameter("email", user.getEmail());
        query.setParameter("id", user.getId());
        query.executeUpdate();
    }

    @Override
    public User findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User WHERE id = :id");
        query.setParameter("id", id);
        Optional<User> user = query.list().stream()
                .findFirst();
        return user.orElseThrow(() -> new UserNotFoundException(locale.getMessage("error.userNotFound")));
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM User WHERE id = :id");
        query.setParameter("id", id).executeUpdate();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User WHERE login = :login");
        query.setParameter("login", login);
        return query.list()
                .stream()
                .findFirst();
    }

}

package com.epam.news.service;

import com.epam.news.model.User;

import java.util.Optional;

public interface UserService extends CRUDService<User> {

    Optional<User> findByLogin(String login);

    User getUserContext();

}

package com.epam.news.repository;

import com.epam.news.model.User;

import java.util.Optional;

public interface UserRepository extends CRUDRepository<User> {

    Optional<User> findByLogin(String login);

}

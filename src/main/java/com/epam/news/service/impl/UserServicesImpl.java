package com.epam.news.service.impl;

import com.epam.news.exception.IncorrectValueException;
import com.epam.news.model.User;
import com.epam.news.repository.UserRepository;
import com.epam.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServicesImpl extends AbstractService<User, UserRepository> implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new IncorrectValueException(locale.getMessage("error.userExists"));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        String passwordFromData = userRepository.findById(user.getId()).getPassword();
        if (!passwordEncoder.matches(passwordFromData, passwordEncoder.encode(user.getPassword()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.update(user);
    }

    @Override
    @Transactional
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }


    @Override
    @Transactional
    public User getUserContext() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return (User)userRepository.findByLogin(login).get();
    }

}

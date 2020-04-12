package com.epam.news.service.impl;

import com.epam.news.exception.IncorrectValueException;
import com.epam.news.model.Role;
import com.epam.news.model.User;
import com.epam.news.repository.UserRepository;
import com.epam.news.util.MessageLocaleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertThrows;

public class UserServiceImplTest {
    private User user;
    @Mock
    private MessageLocaleService localeMock;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserServiceImpl userService;
    @Before
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setName("UserName");
        user.setLogin("UserLogin");
        user.setPassword("UserPass123@");
        user.setRole(Role.AUTHOR);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getLogin(), null, user.getAuthorities())
        );
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveMethodShouldReturnExceptionIfUserExists() {
        Mockito.when(userRepositoryMock.findByLogin(user.getLogin())).thenReturn(Optional.ofNullable(user));
        Mockito.when(localeMock.getMessage("error.userExists")).thenReturn("User already exists");
        Throwable exception = assertThrows(IncorrectValueException.class, () -> userService.save(user));
        Assert.assertEquals("User already exists", exception.getMessage());
    }

    @Test
    public void getUserContextMethodShouldNotNull() {
        Mockito.when(userRepositoryMock.findByLogin(user.getLogin())).thenReturn(Optional.ofNullable(user));
        Assert.assertNotNull(userService.getUserContext());
    }

    @Test(expected = NullPointerException.class)
    public void getUserContextMethodShouldReturnExceptionIfUserNotFound() {
        Mockito.when(userRepositoryMock.findByLogin(user.getLogin())).thenReturn(null);
        Assert.assertNull(userService.getUserContext());
    }
}
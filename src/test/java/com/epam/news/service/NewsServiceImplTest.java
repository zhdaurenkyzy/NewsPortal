package com.epam.news.service;

import com.epam.news.exception.NewsNotFoundException;
import com.epam.news.model.News;
import com.epam.news.model.Role;
import com.epam.news.model.User;
import com.epam.news.repository.NewsRepository;
import com.epam.news.service.impl.NewsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.time.LocalDate;


public class NewsServiceImplTest {
    @Mock
    private NewsRepository newsRepositoryMock;
    @Mock
    private UserService userService;

    private User user;
    private News news;
    @InjectMocks
    private NewsServiceImpl newsService;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(1);
        user.setName("UserName");
        user.setLogin("UserLogin");
        user.setPassword("UserPass123@");
        user.setRole(Role.AUTHOR);

        news = new News();
        news.setId(1);
        news.setNewsTitle("title");
        news.setCurrentDate(LocalDate.now());
        news.setBrief("brief");
        news.setAuthor(user);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findByIdMethodShouldReturnNews() {
        Mockito.when(newsRepositoryMock.findById(1)).thenReturn(news);
        Assert.assertNotNull(newsService.findById(1));
    }

    @Test
    public void findByIdMethodShouldReturnException() {
        Mockito.when(newsRepositoryMock.findById(1)).thenReturn(null);
        Assert.assertNull(newsService.findById(1));
        //проверить что выйдет если newsRepository.findById(1) выкинет ноль
    }
}
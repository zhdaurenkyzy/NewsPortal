package com.epam.news.controller;

import com.epam.news.config.AppInitializer;
import com.epam.news.config.HibernateConfig;
import com.epam.news.config.SecurityConfig;
import com.epam.news.config.WebConfig;
import com.epam.news.service.CommentService;
import com.epam.news.service.NewsService;
import com.epam.news.service.UserService;
import com.epam.news.util.MessageLocaleService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, AppInitializer.class})
@WebAppConfiguration
public class NewsControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private NewsService newsService;
    @Mock
    private CommentService commentService;
    @Mock
    private MessageLocaleService locale;

    @InjectMocks
    private NewsController newsController;
}

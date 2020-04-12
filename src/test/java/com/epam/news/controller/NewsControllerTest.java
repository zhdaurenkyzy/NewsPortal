package com.epam.news.controller;

import com.epam.news.config.AppInitializer;
import com.epam.news.config.HibernateConfig;
import com.epam.news.config.SecurityConfig;
import com.epam.news.config.WebConfig;
import com.epam.news.service.CommentService;
import com.epam.news.service.NewsService;
import com.epam.news.service.UserService;
import com.epam.news.util.MessageLocaleService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, AppInitializer.class})
@WebAppConfiguration
public class NewsControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private NewsService newsService;
    @Mock
    private CommentService commentService;
    @Mock
    private MessageLocaleService locale;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(NewsController.class).build();
    }

    
}

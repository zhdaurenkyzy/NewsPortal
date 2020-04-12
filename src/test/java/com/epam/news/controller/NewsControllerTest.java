package com.epam.news.controller;

import com.epam.news.config.AppInitializer;
import com.epam.news.config.HibernateConfig;
import com.epam.news.config.SecurityConfig;
import com.epam.news.config.WebConfig;
import com.epam.news.model.Comment;
import com.epam.news.model.News;
import com.epam.news.service.CommentService;
import com.epam.news.service.NewsService;
import com.epam.news.util.MessageLocaleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.epam.news.util.Constant.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, AppInitializer.class})
@WebAppConfiguration
public class NewsControllerTest {
    private MockMvc mockMvc;
    private News news;
    private Comment comment;
    private List<News> newsList = new ArrayList<>();;
    private List<Comment> commentList = new ArrayList<>();;
    @Mock
    private NewsService newsService;
    @Mock
    private CommentService commentService;
    @Mock
    private MessageLocaleService locale;
    @InjectMocks
    private NewsController newsController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
        news = new News();
        comment = new Comment();
        newsList.add(news);
        commentList.add(comment);
    }

    @Test
    public void getErrorPageMethodShouldReturnView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error"))
        .andDo(print())
                .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.forwardedUrl("errorPage"));
    }

    @Test
    public void mainShouldViewAndNewsList() throws Exception {
        Mockito.when(newsService.getAll()).thenReturn(newsList);
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(model().attribute(NEWS_LIST_ATTRIBUTE, newsService.getAll()))
                .andExpect(view().name("index"));
    }

    @Test
    public void getAllNewsByAuthor() {
    }

    @Test
    public void continueReadNewsShouldReturnNewsAndCommentList() throws Exception {
        long id = 1;
        Mockito.when(newsService.findById(id)).thenReturn(news);
        Mockito.when(commentService.getAllCommentByNews(id)).thenReturn(commentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/continue/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute(NEWS_ATTRIBUTE, newsService.findById(id)))
                .andExpect(model().attribute(COMMENTS_LIST_ATTRIBUTE, commentService.getAllCommentByNews(id)))
                .andExpect(view().name(NEWS_VIEW_PAGE));
    }

    @Test
    public void getNewsEditingPage() {
    }

    @Test
    public void editNews() {
    }

    @Test
    public void getNewsAddingPage() {
    }

    @Test
    public void addNews() {
    }

    @Test
    public void deleteNews() {
    }

    @Test
    public void deleteModelByListId() {
    }
}

package com.epam.news.controller;

import com.epam.news.config.HibernateConfig;
import com.epam.news.config.SecurityConfig;
import com.epam.news.config.WebConfig;
import com.epam.news.model.Comment;
import com.epam.news.model.News;
import com.epam.news.model.Role;
import com.epam.news.model.User;
import com.epam.news.service.CommentService;
import com.epam.news.service.NewsService;
import com.epam.news.service.UserService;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static com.epam.news.util.Constant.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
public class NewsControllerTest {
    private MockMvc mockMvc;
    private News news;
    private Comment comment;
    private User user;
    private List<News> newsList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();
    @Mock
    private NewsService newsServiceMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private CommentService commentServiceMock;
    @InjectMocks
    private NewsController newsController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
        news = new News();
        news.setId(1);
        comment = new Comment();
        newsList.add(news);
        commentList.add(comment);

        user = new User();
        user.setId(1);
        user.setName("UserName");
        user.setLogin("UserLogin");
        user.setPassword("UserPass123@");
        user.setRole(Role.AUTHOR);

    }

    @Test
    public void getErrorPageMethodShouldReturnView() throws Exception {
        mockMvc.perform(get("/error"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("errorPage"));
    }

    @Test
    public void mainShouldViewAndNewsList() throws Exception {
        Mockito.when(newsServiceMock.getAll()).thenReturn(newsList);
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(model().attribute(NEWS_LIST_ATTRIBUTE, newsServiceMock.getAll()))
                .andExpect(view().name("index"));
    }

    @Test
    public void getAllNewsByAuthor() throws Exception {
        news.setBrief("textBrief");
        news.setNewsTitle("textNewsTitle");
        Mockito.when(userServiceMock.getUserContext()).thenReturn(user);
        Mockito.when(newsServiceMock.getAllNewsByAuthor(user.getId())).thenReturn(newsList);
        mockMvc.perform(get("/newsListByAuthor"))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX_PAGE))
                .andExpect(model().attribute(NEWS_LIST_ATTRIBUTE, hasSize(1)))
                .andExpect(model().attribute(NEWS_LIST_ATTRIBUTE, hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("newsTitle", is(news.getNewsTitle())),
                                hasProperty("brief", is(news.getBrief()))
                        )
                )));
        verify(newsServiceMock, times(1)).getAllNewsByAuthor(user.getId());
        verifyNoMoreInteractions(newsServiceMock);
    }

    @Test
    public void continueReadNewsShouldReturnNewsAndCommentList() throws Exception {
        long id = 1;
        Mockito.when(newsServiceMock.findById(id)).thenReturn(news);
        Mockito.when(commentServiceMock.getAllCommentByNews(id)).thenReturn(commentList);
        mockMvc.perform(get("/continue/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute(NEWS_ATTRIBUTE, newsServiceMock.findById(id)))
                .andExpect(model().attribute(COMMENTS_LIST_ATTRIBUTE, commentServiceMock.getAllCommentByNews(id)))
                .andExpect(view().name(NEWS_VIEW_PAGE));
    }

    @Test
    public void getNewsEditingPageShouldReturnViewAndNewsById() throws Exception {
        long id = 1;
        news.setAuthor(user);
        Mockito.when(userServiceMock.getUserContext()).thenReturn(user);
        Mockito.when(newsServiceMock.findById(id)).thenReturn(news);
        mockMvc.perform(get("/edit/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute(NEWS_ATTRIBUTE, newsServiceMock.findById(id)))
                .andExpect(view().name(NEWS_PAGE));
    }

    @Test
    public void getNewsEditingPageShouldReturnIncorrectValueException() {

    }

    @Test
    public void editNews() throws Exception {
        mockMvc.perform(post("/edit")
                .flashAttr("news", news))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        Mockito.verify(newsServiceMock, times(1)).update(news);

    }

    @Test
    public void getNewsAddingPageShouldReturnViewAndEmptyForm() throws Exception {
        mockMvc.perform(get("/addPage"))
                .andExpect(status().isOk())
                .andExpect(view().name(NEWS_PAGE))
                .andExpect(model().attribute("news", hasProperty("newsTitle", isEmptyOrNullString())))
                .andExpect(model().attribute("news", hasProperty("currentDate", isEmptyOrNullString())))
                .andExpect(model().attribute("news", hasProperty("brief", isEmptyOrNullString())))
                .andExpect(model().attribute("news", hasProperty("context", isEmptyOrNullString())))
                .andExpect(model().attribute("news", hasProperty("author", isEmptyOrNullString())))
                .andDo(print());
        verifyZeroInteractions(newsServiceMock);
    }

    @Test
    public void addNewsShouldReturnValidateError()  {

    }

    @Test
    public void deleteNews() throws Exception {
        long id = 1;
        mockMvc.perform(get("/delete/{id}", id))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        Mockito.verify(newsServiceMock, times(1)).delete(id);

    }

    @Test
    public void deleteModelByListIdShouldReturnRedirection() throws Exception {
        mockMvc.perform(post("/deleteSetNews"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}

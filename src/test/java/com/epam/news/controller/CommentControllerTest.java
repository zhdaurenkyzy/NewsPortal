package com.epam.news.controller;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.epam.news.util.Constant.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
public class CommentControllerTest {
    private MockMvc mockMvc;
    private News news;
    private Comment comment;
    private List<News> newsList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();
    @Mock
    private NewsService newsServiceMock;
    @Mock
    private CommentService commentServiceMock;
    @Mock
    private MessageLocaleService locale;
    @InjectMocks
    private CommentController commentController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        news = new News();
        news.setId(1);
        comment = new Comment();
        comment.setId(1);
        newsList.add(news);
        commentList.add(comment);
    }


    @Test
    public void addCommentShouldSetNewsAndAddComment() throws Exception {
        comment.setTextOfComment("textComment");
        String newsIdParameter = "1";
        long newsId = Long.parseLong(newsIdParameter);
        Mockito.when(newsServiceMock.findById(newsId)).thenReturn(news);
        mockMvc.perform(post("/addComment")
                .param(NEWS_ID_PARAMETER, newsIdParameter))
                .andDo(print())
                .andExpect(view().name("redirect:/continue/" + newsId))
                .andExpect(model().attribute("comment", hasProperty("newsOfComment", notNullValue())))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void addCommentShouldReturnErrorValidate() {

    }

    @Test
    public void editCommentShouldUpdateCommentAndReturnView() throws Exception {
        Mockito.when(commentServiceMock.findNewsIdByCommentId(comment.getId())).thenReturn(1L);
        mockMvc.perform(post("/editComment")
                .flashAttr("comment", comment)
        )
                .andDo(print())
                .andExpect(view().name("redirect:/continue/" + 1))
                .andExpect(status().is3xxRedirection());
        Mockito.verify(commentServiceMock, Mockito.times(1)).update(comment);
    }

    @Test
    public void editCommentShouldReturnErrorValidate() {

    }

    @Test
    public void getCommentEditingPage() throws Exception {
        String commentIdParameter = "1";
        long commentId = Long.parseLong(commentIdParameter);
        Mockito.when(commentServiceMock.findById(commentId)).thenReturn(comment);
        mockMvc.perform(post("/editCommentPage")
                .param(COMMENT_ID_PARAMETER, commentIdParameter))
                .andDo(print())
                .andExpect(view().name(COMMENT_PAGE))
                .andExpect(model().attribute("comment", comment))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteComment() throws Exception {
        String commentIdParameter = "1";
        long commentId = Long.parseLong(commentIdParameter);
        Mockito.when(commentServiceMock.findNewsIdByCommentId(commentId)).thenReturn(1L);
        mockMvc.perform(post("/deleteComment")
                .param(COMMENT_ID_PARAMETER, commentIdParameter))
                .andDo(print())
                .andExpect(view().name("redirect:/continue/" + 1))
                .andExpect(status().is3xxRedirection());
        Mockito.verify(commentServiceMock, Mockito.times(1)).delete(commentId);
    }
}
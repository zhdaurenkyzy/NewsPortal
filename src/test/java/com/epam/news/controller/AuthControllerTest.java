package com.epam.news.controller;

import com.epam.news.config.HibernateConfig;
import com.epam.news.config.SecurityConfig;
import com.epam.news.config.WebConfig;
import com.epam.news.model.Comment;
import com.epam.news.model.News;
import com.epam.news.model.Role;
import com.epam.news.model.User;
import com.epam.news.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
public class AuthControllerTest {
    private MockMvc mockMvc;
    private Comment comment;
    private User user;
    private List<News> newsList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();
    @Mock
    private UserService userServiceMock;
    @Mock
    private MessageLocaleService locale;
    @InjectMocks
    private AuthController authController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        comment = new Comment();
        commentList.add(comment);

        user = new User();
        user.setId(1);
        user.setName("UserName");
        user.setLogin("UserLogin");
        user.setPassword("UserPass123@");
        user.setRole(Role.AUTHOR);
    }


    @Test
    public void getSignInPageShouldReturnErrorMessage() throws Exception {
        Mockito.when(locale.getMessage("error.incorrectLoginPassword")).thenReturn("Incorrect login or password");
        mockMvc.perform(get("/getSignInPage")
                .param("error", "true")
        )
                .andDo(print())
                .andExpect(model().attribute(ERROR_ATTRIBUTE, "Incorrect login or password"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSignUpPageReturnView() throws Exception {
        mockMvc.perform(get("/getSignUpPage"))
                .andDo(print())
                .andExpect(view().name(SIGN_UP_PAGE))
                .andExpect(status().isOk());
    }

    @Test
    public void signUpShouldSaveUserAndReturnView() throws Exception {
        mockMvc.perform(post("/signUp"))
                .andDo(print())
                .andExpect(view().name("redirect:/"))
                .andExpect(model().attribute("user", hasProperty("role", is(Role.COMMENTATOR))))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void signUpShouldReturnError() {

    }

    @Test
    public void getUserEditingPageShouldReturnViewAndUser() throws Exception {
        Mockito.when(userServiceMock.getUserContext()).thenReturn(user);
        mockMvc.perform(get("/editUserPage"))
                .andDo(print())
                .andExpect(view().name(SIGN_UP_PAGE))
                .andExpect(model().attribute(USER_ATTRIBUTE, user))
                .andExpect(status().isOk());
    }

    @Test
    public void editUserShouldUpdateUserAndRedirection() throws Exception {
        mockMvc.perform(post("/editUser")
                .flashAttr("user", user))
                .andDo(print())
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void editUserShouldReturnErrorValidate() {
    }

}
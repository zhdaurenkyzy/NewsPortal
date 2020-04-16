package com.epam.news.controller;


import com.epam.news.config.HibernateConfig;
import com.epam.news.config.SecurityConfig;
import com.epam.news.config.WebConfig;
import com.epam.news.model.Role;
import com.epam.news.model.User;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.epam.news.util.Constant.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
public class AdminControllerTest {
    private MockMvc mockMvc;
    private User user;
    private List<User> userList = new ArrayList<>();
    @Mock
    private UserService userServiceMock;
    @InjectMocks
    private AdminController adminController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        user = new User();
        user.setId(1);
        user.setName("UserName");
        user.setLogin("UserLogin");
        user.setPassword("UserPass123@");
        user.setRole(Role.AUTHOR);
        userList.add(user);

    }

    @Test
    public void adminPageShouldReturnAdminView() throws Exception {
        Mockito.when(userServiceMock.getAll()).thenReturn(userList);
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(model().attribute(USER_LIST_ATTRIBUTE, userList))
                .andExpect(view().name(ADMIN_PAGE));
    }

    @Test
    public void getUserAddingByAdminPageShouldReturnView() throws Exception {
        mockMvc.perform(get("/admin/addUserPage"))
                .andDo(print())
                .andExpect(model().attribute(ROLE_LIST_ATTRIBUTE, EnumSet.of(Role.ADMIN, Role.AUTHOR)))
                .andExpect(view().name(NEW_USER_PAGE))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserByAdminShouldAddUser() throws Exception {
        mockMvc.perform(post("/admin/addUser")
                .param(ROLE_RADIO_PARAMETER, String.valueOf(Role.AUTHOR))
                .flashAttr("user", user))
                .andDo(print())
                .andExpect(model().attribute("user", hasProperty("role", is(Role.AUTHOR))))
                .andExpect(view().name("redirect:/admin"))
                .andExpect(status().is3xxRedirection());
        Mockito.verify(userServiceMock, Mockito.times(1)).save(user);
    }

    @Test
    public void deleteModelByListIdShouldDeleteUser() throws Exception {
        mockMvc.perform(post("/admin/deleteSetUser"))
                .andDo(print())
                .andExpect(view().name("redirect:/admin"))
                .andExpect(status().is3xxRedirection());
    }

}

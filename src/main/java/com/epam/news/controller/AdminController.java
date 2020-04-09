package com.epam.news.controller;

import com.epam.news.model.Role;
import com.epam.news.model.User;
import com.epam.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static com.epam.news.util.Constant.*;


@Controller
public class AdminController extends AbstractController<User, UserService>{
   private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this. userService =  userService;
    }

    private EnumSet<Role> getListRolesForChoice() {
        return EnumSet.of(Role.ADMIN, Role.AUTHOR);
    }

    @GetMapping("/admin")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView(ADMIN_PAGE);
        modelAndView.addObject(USER_LIST_ATTRIBUTE,  userService.getAll());
        return modelAndView;
    }

    @GetMapping("/admin/addUserPage")
    public ModelAndView getUserAddingByAdminPage(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView(NEW_USER_PAGE);
        modelAndView.addObject(ROLE_LIST_ATTRIBUTE, getListRolesForChoice());
        return modelAndView;
    }

    @PostMapping("/admin/addUser")
    public Object addUserByAdmin(@ModelAttribute("user") @Valid User user, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        user.setRole(Role.valueOf(request.getParameter(ROLE_RADIO_PARAMETER)));
        userService.save(user);
        return modelAndView;
    }

    @Override
    @PostMapping("/admin/deleteSetUser")
    public ModelAndView deleteModelByListId(HttpServletRequest request) {
        super.deleteModelByListId(request);
        return new ModelAndView("redirect:/admin");
    }

}

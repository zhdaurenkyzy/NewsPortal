package com.epam.news.controller;

import com.epam.news.model.Role;
import com.epam.news.model.User;
import com.epam.news.service.UserService;
import com.epam.news.util.MessageLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import static com.epam.news.util.Constant.*;

@Controller
public class AuthController {

    private UserService userService;
    private MessageLocaleService locale;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMessageLocaleService(MessageLocaleService locale) {
        this.locale = locale;
    }

    @GetMapping("/getSignInPage")
    public ModelAndView getSignInPage(@ModelAttribute("user") User user,
                                      @RequestParam(name = "error", required = false) Boolean error) {
        ModelAndView modelAndView = new ModelAndView(SIGN_IN_PAGE);
        if (Boolean.TRUE.equals(error)) {
            modelAndView.addObject(ERROR_ATTRIBUTE, locale.getMessage("error.incorrectLoginPassword"));
        }
        return modelAndView;
    }

    @GetMapping("/getSignUpPage")
    public ModelAndView getSignUpPage(@ModelAttribute("user") User user) {
        return new ModelAndView(SIGN_UP_PAGE);
    }

    @PostMapping("/signUp")
    public Object signUp(@ModelAttribute("user") @Valid User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        if (result.hasErrors()) {
            return "/signUpPage";
        }
        user.setRole(Role.COMMENTATOR);
        userService.save(user);
        return modelAndView;
    }

    @GetMapping("/editUserPage")
    public ModelAndView getUserEditingPage(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView(SIGN_UP_PAGE);
        modelAndView.addObject(USER_ATTRIBUTE, userService.getUserContext());
        return modelAndView;
    }

    @PostMapping("/editUser")
    public Object editUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        if (result.hasErrors()) {
            return "/signUpPage";
        }
        userService.update(user);
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(user.getLogin(), null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        return modelAndView;
    }
}

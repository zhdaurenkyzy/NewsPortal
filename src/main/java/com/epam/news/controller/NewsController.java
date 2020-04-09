package com.epam.news.controller;

import com.epam.news.exception.IncorrectValueException;
import com.epam.news.model.Comment;
import com.epam.news.model.News;
import com.epam.news.model.User;
import com.epam.news.service.CommentService;
import com.epam.news.service.NewsService;
import com.epam.news.service.UserService;
import com.epam.news.util.MessageLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import static com.epam.news.util.Constant.*;

@Controller
public class NewsController extends AbstractController<News, NewsService> {
    private UserService userService;
    private NewsService newsService;
    private CommentService commentService;
    private MessageLocaleService locale;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    @Autowired
    public void setMessageLocaleService(MessageLocaleService locale) {
        this.locale = locale;
    }

    @RequestMapping("/errorPage")
    public String getErrorPage() {
        return "errorPage";
    }

    @GetMapping("/")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView(INDEX_PAGE);
        modelAndView.addObject(NEWS_LIST_ATTRIBUTE, newsService.getAll());
        return modelAndView;
    }

    @GetMapping("/newsListByAuthor")
    public ModelAndView getAllNewsByAuthor() {
        ModelAndView modelAndView = new ModelAndView(INDEX_PAGE);
        User user = userService.getUserContext();
        modelAndView.addObject(NEWS_LIST_ATTRIBUTE, newsService.getAllNewsByAuthor(user.getId()));
        return modelAndView;
    }

    @RequestMapping("/continue/{id}")
    public ModelAndView continueReadNews(@PathVariable("id") long id, @ModelAttribute("comment") Comment comment) {
        ModelAndView modelAndView = new ModelAndView(NEWS_VIEW_PAGE);
        modelAndView.addObject(NEWS_ATTRIBUTE, newsService.findById(id));
        modelAndView.addObject(COMMENTS_LIST_ATTRIBUTE, commentService.getAllCommentByNews(id));
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getNewsEditingPage(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView(NEWS_PAGE);
        User user = userService.getUserContext();
        User author = newsService.findById(id).getAuthor();
        if (author.getId() != user.getId()) {
            throw new IncorrectValueException(locale.getMessage("error.ownerArticle"));
        }
        modelAndView.addObject(NEWS_ATTRIBUTE, newsService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit")
    public Object editNews(@ModelAttribute("news") @Valid News news, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        if (result.hasErrors()) {
            return "/newsPage";
        }
        newsService.update(news);
        return modelAndView;
    }

    @GetMapping("/addPage")
    public ModelAndView getNewsAddingPage(@ModelAttribute("news") News news) {
        return new ModelAndView(NEWS_PAGE);
    }

    @PostMapping("/add")
    public Object addNews(@ModelAttribute("news") @Valid News news, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        if (result.hasErrors()) {
            return "/newsPage";
        }
        newsService.save(news);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteNews(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        newsService.delete(id);
        return modelAndView;
    }

    @Override
    @PostMapping("/deleteSetNews")
    public ModelAndView deleteModelByListId(HttpServletRequest request) {
        return super.deleteModelByListId(request);
    }

}

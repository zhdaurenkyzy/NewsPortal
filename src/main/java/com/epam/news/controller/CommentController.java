package com.epam.news.controller;


import com.epam.news.model.Comment;
import com.epam.news.service.CommentService;
import com.epam.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.epam.news.util.Constant.*;

@Controller
public class CommentController {

    private NewsService newsService;
    private CommentService commentService;

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public Object addComment(@ModelAttribute("comment") @Valid Comment comment, BindingResult result, HttpServletRequest request) {
        Long newsId = Long.valueOf(request.getParameter(NEWS_ID_PARAMETER));
        if (result.hasErrors()) {
            return new ModelAndView("forward:/continue/" + newsId, ERROR_MESSAGE_ATTRIBUTE, result.getFieldError().getDefaultMessage() );
        }
        comment.setNewsOfComment(newsService.findById(newsId));
        commentService.save(comment);
        return new ModelAndView("redirect:/continue/" + newsId);
    }

    @PostMapping("/editComment")
    public Object editComment(@ModelAttribute("comment") @Valid Comment comment, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            return "/commentPage";
        }
        modelAndView.setViewName("redirect:/continue/" + commentService.findNewsIdByCommentId(comment.getId()));
        commentService.update(comment);
        return modelAndView;
    }

    @PostMapping("/editCommentPage")
    public Object getCommentEditingPage(@ModelAttribute("comment") Comment comment, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(COMMENT_PAGE);
        Long commentId = Long.valueOf(request.getParameter(COMMENT_ID_PARAMETER));
        modelAndView.addObject(COMMENT_ATTRIBUTE, commentService.findById(commentId));
        return modelAndView;
    }

    @PostMapping("/deleteComment")
    public Object deleteComment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Long commentId = Long.valueOf(request.getParameter(COMMENT_ID_PARAMETER));
        modelAndView.setViewName("redirect:/continue/" + commentService.findNewsIdByCommentId(commentId));
        commentService.delete(commentId);
        return modelAndView;
    }
}

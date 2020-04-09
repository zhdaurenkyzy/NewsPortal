package com.epam.news.service.impl;

import com.epam.news.model.Comment;
import com.epam.news.repository.CommentRepository;
import com.epam.news.service.CommentService;
import com.epam.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl extends AbstractService<Comment, CommentRepository> implements CommentService {

    private CommentRepository commentRepository;
    private UserService userService;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void save(Comment comment) {
        comment.setAuthorOfComment(userService.getUserContext());
        comment.setLocalDate(LocalDate.now());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        comment.setAuthorOfComment(userService.getUserContext());
        comment.setLocalDate(LocalDate.now());
        commentRepository.update(comment);
    }

    @Override
    public List<Comment> getAll() {
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public long findNewsIdByCommentId(long id) {
        return commentRepository.findById(id).getNewsOfComment().getId();
    }

    @Override
    @Transactional
    public List<Comment> getAllCommentByNews(long newsId) {
        return commentRepository.getAllCommentByNews(newsId);
    }

}

package com.epam.news.service;

import com.epam.news.model.Comment;

import java.util.List;

public interface CommentService extends CRUDService<Comment> {

    long findNewsIdByCommentId(long id);

    List<Comment> getAllCommentByNews(long newsId);

}

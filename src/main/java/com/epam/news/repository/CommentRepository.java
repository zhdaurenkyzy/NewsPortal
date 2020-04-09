package com.epam.news.repository;

import com.epam.news.model.Comment;

import java.util.List;

public interface CommentRepository extends CRUDRepository<Comment> {

    List<Comment> getAllCommentByNews(long newsId);

}

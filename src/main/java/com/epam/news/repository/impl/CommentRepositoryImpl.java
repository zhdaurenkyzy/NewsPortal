package com.epam.news.repository.impl;

import com.epam.news.model.Comment;
import com.epam.news.repository.CommentRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends AbstractRepository implements CommentRepository{

    @Override
    public void save(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(comment);
    }

    @Override
    public void update(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "UPDATE Comment set textOfComment = :textOfComment, newsOfComment = :newsOfComment, authorOfComment =:authorOfComment,  localDate= :localDate WHERE id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("textOfComment", comment.getTextOfComment());
        query.setParameter("newsOfComment", findById(comment.getId()).getNewsOfComment());
        query.setParameter("authorOfComment", comment.getAuthorOfComment());
        query.setParameter("localDate", comment.getLocalDate());
        query.setParameter("id", comment.getId());
        query.executeUpdate();
    }

    @Override
    public Comment findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        return Collections.emptyList();
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Comment WHERE id = :id");
        query.setParameter("id", id).executeUpdate();
    }

    @Override
    public List<Comment> getAllCommentByNews(long newsId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Comment WHERE comment_news_id = :newsId";
        Query query = session.createQuery(hql);
        query.setParameter("newsId", newsId);
        return query.list();
    }

}

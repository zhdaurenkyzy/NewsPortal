package com.epam.news.repository.impl;

import com.epam.news.model.News;
import com.epam.news.repository.NewsRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class NewsRepositoryImpl extends AbstractRepository implements NewsRepository {

    @Override
    public void save(News news) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(news);
    }

    @Override
    public void update(News news) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "UPDATE News set newsTitle = :newsTitle, currentDate = :currentDate, brief = :brief, context = :context, author = :author  WHERE id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("newsTitle", news.getNewsTitle());
        query.setParameter("currentDate", findById(news.getId()).getCurrentDate());
        query.setParameter("brief", news.getBrief());
        query.setParameter("context", news.getContext());
        query.setParameter("author", news.getAuthor());
        query.setParameter("id", news.getId());
        query.executeUpdate();
    }

    @Override
    public News findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> root = criteriaQuery.from(News.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        criteriaQuery.select(root);
        TypedQuery<News> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public List<News> getAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> root = criteriaQuery.from(News.class);
        criteriaQuery.select(root);
        TypedQuery<News> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNamedQuery("deleteNewsByIdNamedQuery");
        query.setParameter("id", id).executeUpdate();
    }

    @Override
    public List<News> getAllNewsByAuthor(long userId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM News WHERE author_id = :userId");
        query.setParameter("userId", userId);
        return query.list();
    }

}

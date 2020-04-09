package com.epam.news.service.impl;

import com.epam.news.exception.NewsNotFoundException;
import com.epam.news.model.News;
import com.epam.news.repository.NewsRepository;
import com.epam.news.service.NewsService;
import com.epam.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

@Service
public class NewsServiceImpl extends AbstractService<News, NewsRepository> implements NewsService {

    private NewsRepository newsRepository;
    private UserService userService;

    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void save(News news) {
        news.setCurrentDate(LocalDate.now());
        news.setAuthor(userService.getUserContext());
        newsRepository.save(news);
    }

    @Override
    @Transactional
    public void update(News news) {
        news.setAuthor(userService.getUserContext());
        newsRepository.update(news);
    }

    @Override
    @Transactional
    public News findById(long id) {
        try {
            newsRepository.findById(id);
        } catch (NoResultException e) {
            throw new NewsNotFoundException(locale.getMessage("error.newsNotFound"));
        }
        return newsRepository.findById(id);
    }

    @Override
    @Transactional
    public List<News> getAllNewsByAuthor(long userId) {
        return newsRepository.getAllNewsByAuthor(userId);
    }

}

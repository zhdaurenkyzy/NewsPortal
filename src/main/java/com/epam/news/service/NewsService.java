package com.epam.news.service;

import com.epam.news.model.News;

import java.util.List;

public interface NewsService extends CRUDService<News> {

    List<News> getAllNewsByAuthor(long userId);


}

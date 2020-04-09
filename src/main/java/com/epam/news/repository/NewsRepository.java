package com.epam.news.repository;

import com.epam.news.model.News;

import java.util.List;

public interface NewsRepository extends CRUDRepository<News> {

    List<News> getAllNewsByAuthor(long userId);

}

package com.epam.news.repository;

import com.epam.news.model.AbstractModel;

import java.util.List;

public interface CRUDRepository<E extends AbstractModel> {
    void save(E e);

    void update(E e);

    E findById(long id);

    List<E> getAll();

    void delete(long id);
}

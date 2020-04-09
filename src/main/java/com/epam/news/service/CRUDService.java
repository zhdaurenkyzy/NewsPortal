package com.epam.news.service;

import com.epam.news.model.AbstractModel;

import java.util.List;
import java.util.Set;

public interface CRUDService<E extends AbstractModel> {
    void save(E e);

    void update(E e);

    E findById(long id);

    void delete(long id);

    List<E> getAll();

    void deleteModelListBySetId(Set<Long> id);

}

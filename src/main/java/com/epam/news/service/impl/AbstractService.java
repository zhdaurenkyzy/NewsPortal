package com.epam.news.service.impl;

import com.epam.news.model.AbstractModel;
import com.epam.news.repository.CRUDRepository;
import com.epam.news.service.CRUDService;
import com.epam.news.util.MessageLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Set;

@MappedSuperclass
public abstract class AbstractService<E extends AbstractModel, R extends CRUDRepository<E>> implements CRUDService<E> {
    private R repository;
    protected MessageLocaleService locale;

    @Autowired
    public void setRepository(R repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMessageLocaleService(MessageLocaleService locale) {
        this.locale = locale;
    }


    @Override
    public abstract void save(E e);

    @Override
    public abstract void update(E e);

    @Override
    @Transactional
    public E findById(long id){
       return repository.findById(id);
    }

    @Override
    @Transactional
    public void delete(long id){
        repository.delete(id);
    }

    @Override
    @Transactional
    public List<E> getAll(){
        return repository.getAll();
    }

    @Override
    @Transactional
    public void deleteModelListBySetId(Set<Long> id) {
        id.stream().forEach(x -> delete(x));
    }
}

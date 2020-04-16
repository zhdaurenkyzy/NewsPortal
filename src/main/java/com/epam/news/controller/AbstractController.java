package com.epam.news.controller;

import com.epam.news.model.AbstractModel;
import com.epam.news.service.CRUDService;
import com.epam.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class AbstractController<E extends AbstractModel, S extends CRUDService<E>> {
    private S service;
    @Autowired
    private NewsService newsService;

    @Autowired
    public void setService(S service) {
        this.service = service;
    }


    @PostMapping
    public ModelAndView deleteModelByListId(HttpServletRequest request) {
        Set<Long> listId = new HashSet<>();
        if (request.getParameterValues("listModelCheckbox") != null) {
            Arrays.stream(request.getParameterValues("listModelCheckbox"))
                    .map(Long::parseLong)
                    .forEach(listId::add);
        service.deleteModelListBySetId(listId);

        }
      return new ModelAndView("redirect:/");
    }
}

package com.epam.news.exception;

import com.epam.news.util.MessageLocaleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.news.util.Constant.*;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private Logger logger;
    private MessageLocaleService locale;

    @Autowired
    public void setMessageLocaleService(MessageLocaleService locale) {
        this.locale = locale;
    }

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private ModelAndView handlerGlobal(Exception e, String title) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject(ERROR_TITLE_ATTRIBUTE, title);
        modelAndView.addObject(ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
        logger.info(locale.getMessage("error.infoException") + e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception e) {
        return handlerGlobal(e, locale.getMessage("error.contactAdmin"));
    }

    @ExceptionHandler(CustomGeneralException.class)
    public ModelAndView handlerCustomGeneralException(CustomGeneralException e) {
        return handlerGlobal(e, locale.getMessage("error.infoException"));
    }
}

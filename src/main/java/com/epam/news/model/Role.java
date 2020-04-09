package com.epam.news.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    COMMENTATOR,
    AUTHOR,
    ADMIN;

    Role() {
    }

    @Override
    public String getAuthority() {
        return name();
    }
}

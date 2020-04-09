package com.epam.news.model;

import com.epam.news.validation.CustomValidPassword;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "USER_NEWS_PORTAL")
@AttributeOverrides({
        @AttributeOverride(name = "id", column =
        @Column(name = "USER_ID"))})
public class User extends AbstractModel  {

    @Column(name = "NAME")
    @NotEmpty(message = "{valid.name.required}")
    private String name;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "LOGIN")
    @Size(min = 6, message = "{valid.login.contain}")
    private String login;

    @Column(name = "PASSWORD")
    @CustomValidPassword
    private String password;

    @Column(name = "EMAIL")
    @Email
    @NotEmpty(message = "{valid.email.required}")
    private String email;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.REMOVE})
    private Set<News> news = new HashSet<>();

    @OneToMany(mappedBy = "authorOfComment", cascade = {CascadeType.REMOVE})
    private Set<Comment> comment = new HashSet<>();

    public User() {
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = new ArrayList<>();
        roles.add(getRole());
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }
}

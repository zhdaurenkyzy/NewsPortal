package com.epam.news.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "deleteNewsByIdNamedQuery",
                query = "DELETE FROM News n where n.id =:id"
        )
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column =
        @Column(name = "NEWS_ID"))})
@Table(name = "NEWS")
public class News extends AbstractModel{

    @Column(name = "[NEWS_TITLE]")
    @NotNull(message = "{valid.newsTitle.required}")
    @Size(min = 1, max = 100, message = "{valid.newsTitle.contain}")
    private String newsTitle;

    @Column(name = "[CURRENT_DATE]")
    private LocalDate currentDate;

    @Column(name = "BRIEF")
    @NotNull(message = "{valid.newsBrief.required}")
    @Size(min = 1, max = 1048, message = "{valid.newsBrief.contain}")
    private String brief;

    @Column(name = "CONTEXT")
    @Size(max = 2048, message = "{valid.newsContext.contain}")
    private String context;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID")
    private User author;

    @OneToMany(mappedBy = "newsOfComment", cascade = {CascadeType.REMOVE})
    private Set<Comment> comment = new HashSet<>();

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    public News() {
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }


    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

}

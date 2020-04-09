package com.epam.news.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "COMMENT_NEWS")
@AttributeOverrides({
        @AttributeOverride(name = "id", column =
        @Column(name = "COMMENT_ID"))})
public class Comment extends AbstractModel {

    @Column(name = "COMMENT_TEXT")
    @Size(min = 1, max = 1048, message = "{valid.commentText.contain}")
    private String textOfComment;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "COMMENT_NEWS_ID")
    private News newsOfComment;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMMENT_AUTHOR_ID")
    private User authorOfComment;

    @Column(name = "COMMENT_DATE")
    private LocalDate localDate;

    public String getTextOfComment() {
        return textOfComment;
    }

    public void setTextOfComment(String textOfComment) {
        this.textOfComment = textOfComment;
    }

    public News getNewsOfComment() {
        return newsOfComment;
    }

    public void setNewsOfComment(News newsOfComment) {
        this.newsOfComment = newsOfComment;
    }

    public User getAuthorOfComment() {
        return authorOfComment;
    }

    public void setAuthorOfComment(User authorOfComment) {
        this.authorOfComment = authorOfComment;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}

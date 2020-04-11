package com.epam.news.service;

import com.epam.news.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private CommentService commentService;



}

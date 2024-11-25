package com.xu.service;

import com.xu.pojo.BookRanking;
import com.xu.pojo.UserRanking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VisualizationService {
    public List<BookRanking> getPopularBooks();
    public List<UserRanking> getTopUsers();
}

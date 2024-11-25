package com.xu.service.impl;

import com.xu.mapper.VisualizationMapper;
import com.xu.pojo.BookRanking;
import com.xu.pojo.UserRanking;
import com.xu.service.VisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VisualizationServiceImpl implements VisualizationService {

    @Autowired
    private VisualizationMapper visualizationMapper;

    public List<BookRanking> getPopularBooks() {
        return visualizationMapper.getPopularBooks();
    }

    public List<UserRanking> getTopUsers() {
        return visualizationMapper.getTopUsers();
    }
}

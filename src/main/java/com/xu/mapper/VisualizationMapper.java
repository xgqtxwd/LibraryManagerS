package com.xu.mapper;

import com.xu.pojo.BookRanking;
import com.xu.pojo.UserRanking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VisualizationMapper {
    List<BookRanking> getPopularBooks();
    List<UserRanking> getTopUsers();
}

package com.xu.controller;

import com.xu.pojo.BookRanking;
import com.xu.pojo.Result;
import com.xu.pojo.UserRanking;
import com.xu.service.VisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 标识该类为 RESTful API 控制器
@RequestMapping("/visualization") // 定义控制器的根路径
public class VisualizationController {

    @Autowired // 自动注入可视化服务实现类
    private VisualizationService visualizationServiceImpl;

    /**
     * 获取热门书籍排行榜
     *
     * @return 包含书籍排行榜列表的响应结果
     */
    @GetMapping("/popular-books") // 处理获取热门书籍排行榜的 GET 请求
    public Result<List<BookRanking>> getPopularBooks() {
        List<BookRanking> bookRankings=visualizationServiceImpl.getPopularBooks();
        return Result.success(bookRankings);
    }

    /**
     * 获取用户排行榜
     *
     * @return 包含用户排行榜列表的响应结果
     */
    @GetMapping("/top-users") // 处理获取用户排行榜的 GET 请求
    public Result<List<UserRanking>> getTopUsers() {
        List<UserRanking> userRankings=visualizationServiceImpl.getTopUsers();
        return Result.success(userRankings);
    }
}

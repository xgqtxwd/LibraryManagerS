package com.xu.controller;

import com.xu.pojo.Announcement;
import com.xu.pojo.Result;
import com.xu.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 标识该类为 RESTful API 控制器
@RequestMapping("/home") // 定义控制器的根路径
public class AnnouncementController {

    @Autowired // 自动注入公告服务实现类
    private AnnouncementService announcementServiceImpl;

    /**
     * 获取所有有效的公告列表
     *
     * @return 包含公告列表的响应结果
     */
    @GetMapping("/announcements") // 处理获取公告列表的 GET 请求
    public Result<List<Announcement>> getAnnouncements() {
        List<Announcement> announcements = announcementServiceImpl.getActiveAnnouncements();
        return Result.success(announcements);
    }

    /**
     * 获取所有公告列表（包括已过期的公告）
     *
     * @return 包含公告列表的响应结果
     */
    @GetMapping("/admin/announcements") // 处理获取所有公告列表的 GET 请求
    public Result<List<Announcement>>  getAllAnnouncements() {
       List<Announcement> announcementList= announcementServiceImpl.getAllAnnouncements();
       return Result.success(announcementList);
    }

    /**
     * 添加新的公告
     *
     * @param announcement 包含公告信息的请求体
     * @return 操作成功的响应结果
     */
    @PostMapping("/admin/announcements") // 处理添加公告的 POST 请求
    public Result addAnnouncement(@RequestBody Announcement announcement) {
        announcementServiceImpl.addAnnouncement(announcement);
        return Result.success();
    }

    /**
     * 更新公告信息
     *
     * @param id 公告的唯一标识符
     * @param announcement 包含更新信息的请求体
     * @return 操作成功的响应结果
     */
    @PutMapping("/admin/announcements/{id}") // 处理更新公告的 PUT 请求
    public Result updateAnnouncement(@PathVariable int id, @RequestBody Announcement announcement) {
        announcement.setId(id);
        announcementServiceImpl.updateAnnouncement(announcement);
        return Result.success();
    }

    /**
     * 删除指定的公告
     *
     * @param id 公告的唯一标识符
     * @return 操作成功的响应结果
     */
    @DeleteMapping("/admin/announcements/{id}") // 处理删除公告的 DELETE 请求
    public Result deleteAnnouncement(@PathVariable int id) {
        announcementServiceImpl.deleteAnnouncement(id);
        return Result.success();
    }
}


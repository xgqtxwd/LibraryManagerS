package com.xu.controller;

import com.xu.pojo.Announcement;
import com.xu.pojo.Result;
import com.xu.service.AnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

      @Autowired
      private AnnouncementService announcementService;
//获取公告列表
      @GetMapping
      public Result<List<Announcement>> getAnnouncements() {
            List<Announcement> announcements = announcementService.getActiveAnnouncements();
            return Result.success(announcements);
      }
}

package com.xu.service.impl;

import com.xu.mapper.AnnouncementMapper;
import com.xu.pojo.Announcement;
import com.xu.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    public List<Announcement> getActiveAnnouncements() {
        return announcementMapper.getActiveAnnouncements();
    }
}

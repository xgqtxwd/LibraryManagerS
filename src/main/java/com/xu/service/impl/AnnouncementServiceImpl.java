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
    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementMapper.getAllAnnouncements();
    }

    @Override
    public void addAnnouncement(Announcement announcement) {
        announcementMapper.addAnnouncement(announcement);
    }
    @Override
    public void updateAnnouncement(Announcement announcement) {
        announcementMapper.updateAnnouncement(announcement);
    }
    @Override
    public void deleteAnnouncement(int id) {
        announcementMapper.deleteAnnouncement(id);
    }

    public List<Announcement> getActiveAnnouncements() {
        return announcementMapper.getActiveAnnouncements();
    }
}

package com.xu.service;

import com.xu.pojo.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnouncementService {

    public List<Announcement> getActiveAnnouncements();
}

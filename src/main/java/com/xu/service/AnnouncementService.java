package com.xu.service;

import com.xu.pojo.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnouncementService {

    public List<Announcement> getActiveAnnouncements();
    public List<Announcement> getAllAnnouncements() ;

    public void addAnnouncement(Announcement announcement) ;

    public void updateAnnouncement(Announcement announcement);

    public void deleteAnnouncement(int id) ;



}

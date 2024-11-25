package com.xu.mapper;

import com.xu.pojo.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public  interface AnnouncementMapper {
    @Select("SELECT * FROM announcements WHERE is_active = true ORDER BY publish_date DESC")
    List<Announcement> getActiveAnnouncements();

    List<Announcement> getAllAnnouncements();

    void addAnnouncement(Announcement announcement);

    void updateAnnouncement(Announcement announcement);

    void deleteAnnouncement(int id);
}


package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    private Integer id; // 公告ID
    private String title; // 公告标题
    private String content; // 公告内容
    private LocalDateTime publishDate; // 发布日期
    private LocalDateTime expirationDate; // 过期日期
    private Boolean isActive; // 是否有效
    private Integer createUserId; // 创建人ID
}

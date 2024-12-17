package com.xu.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PenaltyBorrowRecords {
    private int overdueId;
    private String title;//文章标题
    private String coverImg;//封面图像
    private String writer;//作者
    private LocalDateTime borrowingTime; // 借阅日期
    private LocalDateTime endTime;
    private double penalty;
    private int status;
}

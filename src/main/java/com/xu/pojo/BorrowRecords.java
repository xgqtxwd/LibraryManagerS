package com.xu.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecords {
    private Integer id; // 借阅记录ID
    private Integer userId; // 用户ID
    private String username; // 用户对象
    private Integer bookId; // 图书ID
    private LocalDateTime borrowingTime; // 借阅日期
    private LocalDateTime endTime; // 归还日期

    private Boolean isReturn; // 是否已归还
}

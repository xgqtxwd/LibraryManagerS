package com.xu.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecords {
    private Integer id; // 借阅记录ID
    private Integer userId; // 用户ID
    private Integer bookId; // 图书ID
    private LocalDateTime borrowDate; // 借阅日期
    private LocalDateTime returnDate; // 归还日期
    private Boolean isReturn; // 是否已归还
}

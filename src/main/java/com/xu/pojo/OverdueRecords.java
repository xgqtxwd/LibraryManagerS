package com.xu.pojo;

import lombok.Data;

@Data
public class OverdueRecords {
    private int overdueId;
    private int borrowId;
    private int overdueDays;
    private double penalty;
    private int status;
}

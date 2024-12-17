package com.xu.service;

import com.xu.pojo.PenaltyBorrowRecords;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PenaltyService {
    void addOverdueRecord(Integer id, int overdueDays, double penalty);

    List<PenaltyBorrowRecords> getRecords(Integer personId);
}

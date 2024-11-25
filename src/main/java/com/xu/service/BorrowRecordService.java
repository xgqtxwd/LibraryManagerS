package com.xu.service;

import com.xu.pojo.BorrowRecords;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowRecordService {
    public List<BorrowRecords> getOverdueRecords();
}

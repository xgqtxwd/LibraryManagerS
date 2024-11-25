package com.xu.service.impl;

import com.xu.mapper.BorrowRecordMapper;
import com.xu.pojo.BorrowRecords;
import com.xu.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    public List<BorrowRecords> getOverdueRecords() {
        return borrowRecordMapper.findOverdueRecords();
    }
}
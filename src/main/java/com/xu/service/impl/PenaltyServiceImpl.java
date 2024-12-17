package com.xu.service.impl;

import com.xu.mapper.PenaltyServiceMapper;
import com.xu.pojo.PenaltyBorrowRecords;
import com.xu.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenaltyServiceImpl implements PenaltyService {
    @Autowired
    private PenaltyServiceMapper penaltyServiceMapper;
    @Override
    public void addOverdueRecord(Integer id, int overdueDays, double penalty) {
        penaltyServiceMapper.addOverdueRecord(id, overdueDays, penalty);
    }

    @Override
    public List<PenaltyBorrowRecords> getRecords(Integer personId) {
      return penaltyServiceMapper.getRecords(personId);

    }
}

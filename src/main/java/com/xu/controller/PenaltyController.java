package com.xu.controller;

import com.xu.pojo.BorrowRecords;
import com.xu.pojo.OverdueRecords;
import com.xu.pojo.PenaltyBorrowRecords;
import com.xu.pojo.Result;
import com.xu.service.BorrowRecordService;
import com.xu.service.PenaltyService;
import com.xu.utils.PenaltyServiceUtil;
import com.xu.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/penalty")
public class PenaltyController {
    @Autowired
    private PenaltyService penaltyServiceImpl;
    @Autowired
    private BorrowRecordService borrowRecordServiceImpl;
    @Autowired
    private PenaltyServiceUtil penaltyServiceUtil;

    @GetMapping("/overdue")
    public  Result<List<PenaltyBorrowRecords>> getOverdueRecords() {
        List<BorrowRecords> overdueRecords =borrowRecordServiceImpl.getOverdueRecords();
        //将逾期记录插入罚金表中，并计算罚金同时插入
        for (BorrowRecords record : overdueRecords){
            int overdueDays = (int) ChronoUnit.DAYS.between(record.getBorrowingTime(),record.getEndTime());
            double penalty = penaltyServiceUtil.calculatePenalty(overdueDays);
            penaltyServiceImpl.addOverdueRecord(record.getId(),overdueDays,penalty);
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer personId= (Integer) map.get("id");
        List<PenaltyBorrowRecords> penaltyRecords =penaltyServiceImpl.getRecords(personId);
        return Result.success(penaltyRecords);
    }
    @GetMapping("/isPayed")
    public Result<Integer> isPayed(@RequestParam Integer bookId){
        return Result.success(borrowRecordServiceImpl.isPaid(bookId));
    }
//    @GetMapping("/records")
//    public Result<List<PenaltyBorrowRecords>> getOverdueRecords() {
//        Map<String, Object> map = ThreadLocalUtil.get();
//        Integer personId= (Integer) map.get("id");
//        List<PenaltyBorrowRecords> overdueRecords = penaltyServiceImpl.getOverdueRecords();
//        return Result.success(overdueRecords);
//    }
}


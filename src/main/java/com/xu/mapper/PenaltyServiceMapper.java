package com.xu.mapper;

import com.xu.pojo.OverdueRecords;
import com.xu.pojo.PenaltyBorrowRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PenaltyServiceMapper {
    @Update("update overdue_records set status=1 where overdue_id=#{id}")
     void updateRecords(int overdueId);

    @Insert("insert ignore into overdue_records (borrow_id,overdue_days,penalty) values (#{id},#{overdueDays},#{penalty}) ")
    void addOverdueRecord(Integer id, int overdueDays, double penalty);
@Select("select overdue_id,title,cover_img,writer,borrowing_time,end_time,penalty from overdue_records,borrow_records where borrow_records.user_id=#{personId} and overdue_records.borrow_id=borrow_records.id and status=0  ")
     List<PenaltyBorrowRecords> getRecords(Integer personId);
@Select("select * from overdue_records where overdue_id =#{id} and status=0")
    OverdueRecords getRecordById(Integer id);
}

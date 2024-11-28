package com.xu.mapper;

import com.xu.pojo.BorrowRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BorrowRecordMapper {
    @Select("SELECT * FROM borrow_records WHERE isReturn = false AND end_time < NOW()")
    List<BorrowRecords> findOverdueRecords();
    @Select("SELECT * FROM borrow_records WHERE isReturn = false AND end_time < NOW() AND user_id=#{personId}")
    List<BorrowRecords> findOverdueRecordsById(Integer personId);

}


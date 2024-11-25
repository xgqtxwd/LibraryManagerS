package com.xu.mapper;

import com.xu.pojo.Book;
import com.xu.pojo.BorrowRecords;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> listBooks(String title, String writer);

    //借书处理
    @Update("update book set quantity=#{quantity} where id=#{id}")
    void borrowBook(Book book);
    @Insert("insert into borrow_records(book_id,user_id,borrowing_time,end_time,title,cover_img,writer) values(#{id},#{userId},#{borrowingTime},#{endTime},#{title},#{coverImg},#{writer})")
    void insertRecords(Book book);

   //还书处理
    @Update("update book set quantity=quantity+1 where id=#{bookId}")
    void returnBook(Integer bookId);
    @Update("update borrow_records set isreturn= 1 ,borrow_records.end_time=now() where id=#{id}")
    void updateRecord(Integer id);

    //更新图书信息
    @Update("update book set title=#{title},content=#{content},writer=#{writer},category_id=#{categoryId},cover_img=#{coverImg},quantity=#{quantity} where id=#{id}")
    void updateBook(Book book);

   @Select("select * from book where id=#{id}")
    Book searchBooks(Integer id);



    List<Book> listReturnBooks(Integer userId,String title, String writer);
@Select("select * from borrow_records where id=#{id}")
BorrowRecords searchInRecords(Integer id);
@Insert("insert into book(title,content,writer,category_id,cover_img,quantity) values(#{title},#{content},#{writer},#{categoryId},#{coverImg},#{quantity})")
    void addBook(Book book);
}

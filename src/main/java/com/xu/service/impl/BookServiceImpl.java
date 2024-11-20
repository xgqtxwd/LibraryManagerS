package com.xu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xu.mapper.BookMapper;
import com.xu.pojo.Book;
import com.xu.pojo.BorrowRecords;
import com.xu.pojo.PageBean;
import com.xu.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookMapper bookMapper;
    @Override
    public PageBean<Book> listBooks(Integer pageNum, Integer pageSize, String title, String writer) {
        //创建pageBean对象
        PageBean<Book> bookPageBean = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper\
       List<Book> bookslist = bookMapper.listBooks(title,writer);
       Page<Book> pages =(Page<Book>)bookslist;
       bookPageBean.setTotal(pages.getTotal());
       bookPageBean.setItems(pages.getResult());
       return bookPageBean;
    }

    @Override
    public void borrowBook(Book book) {
        bookMapper.insertRecords(book);
        bookMapper.borrowBook(book);
    }

    @Override
    public void returnBook(Integer id) {

        bookMapper.updateRecord(id);
        log.info(id.toString());
        BorrowRecords records= bookMapper.searchInRecords(id);
        Integer bookId=records.getBookId();
        log.info(bookId.toString());
        bookMapper.returnBook(bookId);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

    @Override
    public Book searchBooks(Integer id) {
        return bookMapper.searchBooks(id);
    }

    @Override
    public PageBean<Book> listReturnBooks(Integer userId, Integer pageNum, Integer pageSize, String title, String writer) {
        //创建pageBean对象
        PageBean<Book> bookPageBean = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper\
        List<Book> bookslist = bookMapper.listReturnBooks(userId,title,writer);
        Page<Book> pages =(Page<Book>)bookslist;
        bookPageBean.setTotal(pages.getTotal());
        bookPageBean.setItems(pages.getResult());
        return bookPageBean;
    }

}

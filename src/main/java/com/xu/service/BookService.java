package com.xu.service;

import com.xu.pojo.Book;
import com.xu.pojo.PageBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    PageBean<Book> listBooks(Integer pageNum, Integer pageSize, String title, String writer);

    void borrowBook(Book book);

    void returnBook(Integer id);

    void updateBook(Book book);

    Book searchBooks(Integer id);

    PageBean<Book> listReturnBooks(Integer userId,Integer pageNum, Integer pageSize, String title, String writer);

}

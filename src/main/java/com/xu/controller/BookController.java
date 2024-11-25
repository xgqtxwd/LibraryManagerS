package com.xu.controller;
import com.xu.pojo.PageBean;
import com.xu.pojo.User;
import com.xu.utils.IsAdminUtil;
import com.xu.utils.ThreadLocalUtil;

import com.xu.pojo.Book;
import com.xu.pojo.Result;
import com.xu.service.BookService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xu.service.UserService;

import java.util.Map;

import static java.time.LocalDateTime.now;

@Slf4j
@RestController
@RequestMapping("/book")

public class BookController {
    @Autowired
    private IsAdminUtil isAdminUtil;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userServiceImpl;

    // 获取书籍列表

    @GetMapping("/borrow")
    public Result<PageBean<Book>> listBooks(Integer pageNum, Integer pageSize, @RequestParam(required = false) String title, @RequestParam(required = false ) String writer)
    {
        log.info(pageNum + "-" + pageSize + "-" + title+writer);
        PageBean<Book> books = bookService.listBooks(pageNum,pageSize,title,writer);
        return Result.success(books);
    }

    // 借阅书籍
    @GetMapping("/borrowed")
    public Result borrowBook(Integer id) {
        // 获取当前登录用户的id
        Map<String,Object> map= ThreadLocalUtil.get();
        Book book=bookService.searchBooks(id);
        if(book.getQuantity()==0){
            return Result.error("图书库存不足");
        }
        book.setUserId((Integer) map.get("id"));
        book.setBorrowingTime(now());
        book.setEndTime(now().plusDays(30));
        book.setQuantity(book.getQuantity()-1);
        bookService.borrowBook(book);
        return Result.success();
    }



    // 更新书籍信息
    @PutMapping("/update")
    public Result updateBook(@RequestBody Book book) {
        // 检查用户是否为管理员
        if(!isAdminUtil.isAdminUser())
        {
            return Result.error("您没有权限执行此操作");
        }
        bookService.updateBook(book);
        return Result.success();
    }

//展示还书列表
@GetMapping("/still")
public Result<PageBean<Book>> listReturnBooks(Integer pageNum, Integer pageSize, @RequestParam(required = false) String title, @RequestParam(required = false ) String writer)
{   Map<String, Object> map = ThreadLocalUtil.get();
    Integer id= (Integer) map.get("id");
    log.info(pageNum + "-" + pageSize + "-" + title+writer);
    PageBean<Book> books = bookService.listReturnBooks(id,pageNum,pageSize,title,writer);
    return Result.success(books);
}
    // 归还书籍
    @GetMapping("/return")
    public Result returnBook(Integer id) {

        bookService.returnBook(id);
        return Result.success();
    }
    @PostMapping("/add")
    public Result addBook(@RequestBody Book book) {
        // 检查用户是否为管理员
        if(!isAdminUtil.isAdminUser())
        {
            return Result.error("您没有权限执行此操作");
        }
        bookService.addBook(book);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result deleteBook(@PathVariable Integer id) {
        // 检查用户是否为管理员
        if(!isAdminUtil.isAdminUser())
        {
            return Result.error("您没有权限执行此操作");
        }
        bookService.deleteBook(id);
        return Result.success();
    }
//
//    @GetMapping("/detail")
//    public Result<Book> detail(Integer id) {
//        Book book = bookService.findById(id);
//        return Result.success(book);
//    }
//
//    @PostMapping("/update")
//    public Result updateBook(@RequestBody Book book) {
//        bookService.updateBook(book);
//        return Result.success();

}

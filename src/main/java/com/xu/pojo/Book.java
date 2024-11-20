package com.xu.pojo;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Book {
    private Integer id;//主键ID
    private String title;//文章标题
    private String content;//文章内容
    private String coverImg;//封面图像
    private String writer;//作者
    private Integer categoryId;//文章分类id
    private Integer userId;//创建人ID
    private LocalDateTime borrowingTime;//借阅时间
    private LocalDateTime endTime;//到期时间
    private Integer quantity;//图书存量
}
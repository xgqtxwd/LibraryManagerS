package com.xu.service;

import com.xu.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    void insertCategory(Category category);
//列表查询
    List<Category> listCategory();
//根据id查询
    Category findById(Integer id);

    void update(Category category);

    void deleteCategory(int id);
}

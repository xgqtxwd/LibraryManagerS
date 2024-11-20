package com.xu.service.impl;

import com.xu.mapper.CategoryMapper;
import com.xu.pojo.Category;
import com.xu.service.CategoryService;
import com.xu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void insertCategory(Category category) {
        category.setCreateTime(now());
        category.setUpdateTime(now());
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        category.setCreateUser(id);
        categoryMapper.insertCategory(category);
    }

    @Override
    public List<Category> listCategory() {
        //获取当前登录用户的id
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        return categoryMapper.listCategory(id);

    }

    @Override
    public Category findById(Integer id) {

        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category)
    {
        category.setUpdateTime(now());
        System.out.println(category.toString());
        categoryMapper.update(category);
    }
    @Override
    public void deleteCategory(int id) {
        categoryMapper.deleteCategory(id);
    }

}

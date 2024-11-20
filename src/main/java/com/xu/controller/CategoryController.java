package com.xu.controller;

import com.xu.mapper.CategoryMapper;
import com.xu.mapper.UserMapper;
import com.xu.pojo.Category;
import com.xu.pojo.Result;
import com.xu.pojo.User;
import com.xu.service.CategoryService;
import com.xu.service.UserService;
import com.xu.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userServiceImpl;
    @PostMapping("/insert")
    public Result insertCategory(@RequestBody Category category) {
        //获取当前登录用户的id
        Map<String,Object> map= ThreadLocalUtil.get();
        String username= (String)map.get("username");
        log.info("请求插入分类的人username:"+username);
        User user = userServiceImpl.findByUserName(username);
        //判断用户是否为管理员
        if(user.getPrivilegeLevel()!=1){
                return Result.error("权限不足，请联系管理员");
        }
        categoryService.insertCategory(category);
        return Result.success();
    }
    @DeleteMapping("/delete")
    public Result deleteCategory(Integer id) {
        //获取当前登录用户的id
        Map<String,Object> map= ThreadLocalUtil.get();
        String username= (String)map.get("username");
        log.info("username:"+username);
        User user = userServiceImpl.findByUserName(username);
        //判断用户是否为管理员
        if(user.getPrivilegeLevel()!=1){
            return Result.error("权限不足，请联系管理员");
        }
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @GetMapping("")
    public Result<List<Category>> listCategory() {
     List<Category> categories =categoryService.listCategory();
     return Result.success(categories);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id){

        Category category = categoryService.findById(id);
        return Result.success(category);
    }
   @PutMapping("/update")
    public Result UpdateCategory(@RequestBody Category category){
       Map<String,Object> map= ThreadLocalUtil.get();
       String username= (String)map.get("username");
       log.info("请求插入分类的人username:"+username);
       User user = userServiceImpl.findByUserName(username);
       //判断用户是否为管理员
       if(user.getPrivilegeLevel()!=1){
           return Result.error("权限不足，请联系管理员");
       }
        categoryService.update(category);
        return Result.success();
    }
}

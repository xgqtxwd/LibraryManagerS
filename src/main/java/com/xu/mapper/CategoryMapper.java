package com.xu.mapper;

import com.xu.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Delete("delete from category where id=#{id}")
    void deleteCategory(int id);
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);
    @Insert("insert into category (create_time,category_alias,category_name,create_user,update_time) VALUES (#{createTime},#{categoryAlias},#{categoryName},#{createUser},#{updateTime})")
    void insertCategory(Category category);
    @Select("select * from category where create_user=#{id}")
   List<Category> listCategory(Integer id);
    @Select("select * from category where id=#{id}")
    Category findById(Integer id);
}

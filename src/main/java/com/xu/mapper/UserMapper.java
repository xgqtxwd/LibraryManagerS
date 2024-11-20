package com.xu.mapper;

import com.xu.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("insert into user(username,password,create_time,update_time) values(#{username},#{md5Password},now(),now())")
    void insert(String username, String md5Password);
   @Select("select * from user where username = #{username}")
   User findByUserName(String username);
   @Update("update user set nickname=#{nickname} ,username=#{username},email=#{email},user.update_time=now() where id = #{id}")
    void update(User user);
   @Update("update user set user_pic=#{avatar},user.update_time=now() where id = #{id}")
    void updateAvatar(String avatar,Integer id);
   @Update("update user set password=#{md5String},user.update_time=now() where id = #{id}")
    void updatePassword(String md5String,Integer id);
}

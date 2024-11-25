package com.xu.mapper;

import com.xu.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //普通用户的功能开发
    @Insert("insert into user(username,password,create_time,update_time) values(#{username},#{md5Password},now(),now())")
    void insert(String username, String md5Password);
   @Select("select * from user where username = #{username}")
   User findByUserName(String username);
   @Update("update user set nickname=#{nickname} ,username=#{username},email=#{email},user.update_time=now() where id = #{id}")
    void update(User user);
   @Update("update user set user_pic=#{avatarData},user.update_time=now() where id = #{id}")
    void updateAvatar(String avatarData,Integer id);
   @Update("update user set password=#{md5String},user.update_time=now() where id = #{id}")
    void updatePassword(String md5String,Integer id);
    @Select("select * from user where id = #{id}")
    User findById(Integer id);
    //管理员对用户管理界面功能开发
    // 查询所有用户
    @Select("SELECT * FROM user")
    List<User> getAllUsers();

    // 添加用户
    @Insert("INSERT INTO user (username, password, nickname, email, privilegelevel) VALUES (#{username}, #{password}, #{nickname}, #{email}, #{privilegeLevel})")
    void addUser(User user);

    // 更新用户
    @Update("UPDATE user SET username = #{username}, password = #{password}, nickname = #{nickname}, email = #{email}, privilegelevel = #{privilegeLevel} WHERE id = #{id}")
    void updateUser(User user);

    // 删除用户
    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(int id);

}

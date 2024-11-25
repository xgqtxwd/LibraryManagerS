package com.xu.service.impl;


import com.xu.mapper.UserMapper;
import com.xu.pojo.User;
import com.xu.service.UserService;
import com.xu.utils.Md5Util;
import com.xu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        return null;
    }

    @Override
    public void register(String username, String password) {
        String md5Password = Md5Util.getMD5String(password);
        userMapper.insert(username, md5Password);

    }


    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);

    }



    @Override
    public void updateAvatar(String avatarData) {
        //获取当前登录用户的id
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        userMapper.updateAvatar(avatarData,id);
    }

    @Override
    public void updatePassword(String newPwd) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        userMapper.updatePassword(Md5Util.getMD5String(newPwd),id);
    }

    @Override
    public User findByUserId(Integer id) {
        User user = userMapper.findById(id);
        return user;
    }

    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void updatePws(Integer id, String newPassword) {
        userMapper.updatePassword(Md5Util.getMD5String(newPassword),id);
    }
}

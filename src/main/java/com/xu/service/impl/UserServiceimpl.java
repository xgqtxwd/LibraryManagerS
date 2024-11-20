package com.xu.service.impl;


import com.xu.mapper.UserMapper;
import com.xu.pojo.User;
import com.xu.service.UserService;
import com.xu.utils.Md5Util;
import com.xu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceimpl implements UserService {

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
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);

    }



    @Override
    public void updateAvatar(String avatar) {
        //获取当前登录用户的id
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        userMapper.updateAvatar(avatar,id);
    }

    @Override
    public void updatePassword(String newPwd) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        userMapper.updatePassword(Md5Util.getMD5String(newPwd),id);
    }
}

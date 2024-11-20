package com.xu.service;


import com.xu.pojo.User;

public interface UserService {

    User login(String username, String password);
    void register(String username, String password);
    User findByUserName(String username) ;
    void update(User user);

    void updateAvatar(String avatar);

    void updatePassword(String newPwd);
}

package com.xu.service;


import com.xu.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User login(String username, String password);
    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatar);

    void updatePassword(String newPwd);
    User findByUserId(Integer id);
    User findByUserName(String id);
    // 管理员业务方法
    public List<User> getAllUsers() ;

    public void addUser(User user);


    public void updateUser(User user);

    public void deleteUser(int id) ;

    void updatePws(Integer id, String newPassword);
}

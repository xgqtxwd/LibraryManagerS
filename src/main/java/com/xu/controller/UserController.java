package com.xu.controller;

import com.xu.pojo.PasswordChangeRequest;
import com.xu.pojo.Result;
import com.xu.pojo.User;
import com.xu.service.UserService;
import com.xu.utils.JwtUtils;
import com.xu.utils.Md5Util;
import com.xu.utils.ThreadLocalUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userServiceImpl;

    @PostMapping("/user/register")
    public Result list(String username, String password) {
        User user = userServiceImpl.findByUserName(username);
        if (user != null) {
            return Result.error("用户名已存在");
        } else {
            userServiceImpl.register(username, password);
            return Result.success();
        }
    }

    @PostMapping("/user/login")
    public Result<String> login(String username, String password) {
       User user = userServiceImpl.findByUserName(username);

        if (user == null) {
            return Result.error("用户名不存在");
        }
        if (Md5Util.getMD5String(password).equals(user.getPassword())) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", user.getUsername());
            map.put("id", user.getId());
            String jwt= JwtUtils.generateJwt(map);
            return Result.success(jwt);
        }

        return Result.error("密码错误");
    }
    @GetMapping("/user/userInfo")
    public Result<User> userInfo(/*@RequestHeader("Authorization" ) String token*/) {
       /* Map<String, Object> map = JwtUtils.parseJWT(token);
        String username = (String) map.get("username");*/
        //获取当前登录用户的id
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        log.info("id:{}",id);
        User user = userServiceImpl.findByUserId(id);
        return Result.success(user);
    }
    @PutMapping("/user/update")
    public Result update(@RequestBody User user) {
        userServiceImpl.update(user);
        return Result.success();
    }
    @PatchMapping("/user/avatar")
   public Result updateAvatar(@RequestParam @URL String avatarData) {
        userServiceImpl.updateAvatar(avatarData);
        return Result.success();
   }
   @PatchMapping("/user/updatePwd")
   public Result updatePassword(@RequestBody Map<String, String> map) {
     String oldPwd= map.get("old_pwd");
     String newPwd= map.get("new_pwd");
     String rePwd= map.get("re_pwd");
     if(!newPwd.equals(rePwd)){
         return Result.error("两次密码不一致");
     }
       Map<String,Object> map2 = ThreadLocalUtil.get();
       String username = (String) map2.get("username");
       User user = userServiceImpl.findByUserName(username);
       if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
           return Result.error("原密码输入错误");
       }
           userServiceImpl.updatePassword(newPwd);
           return Result.success();

   }
// 管理员业务方法
    @GetMapping("/home/admin/users")
    public Result<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();
        return Result.success(users);
    }

    @PostMapping("/home/admin/users")
    public Result addUser(@RequestBody User user) {
        userServiceImpl.addUser(user);
        return Result.success();
    }

    @PutMapping("/home/admin/users/{id}")
    public Result updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        userServiceImpl.updateUser(user);
        return Result.success();
    }

    @DeleteMapping("/home/admin/users/{id}")
    public Result deleteUser(@PathVariable int id) {
        userServiceImpl.deleteUser(id);
        return Result.success();
    }
    @PutMapping("/home/admin/users")
    public Result updatePws(@RequestBody PasswordChangeRequest request){
        userServiceImpl.updatePws(request.getId(),request.getNewPassword());
        return Result.success();
    }

}





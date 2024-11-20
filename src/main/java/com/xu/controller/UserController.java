package com.xu.controller;

import com.xu.pojo.Result;
import com.xu.pojo.User;
import com.xu.service.UserService;
import com.xu.utils.JwtUtils;
import com.xu.utils.Md5Util;
import com.xu.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userServiceImpl;

    @PostMapping("/register")
    public Result list(String username, String password) {
        User user = userServiceImpl.findByUserName(username);
        if (user != null) {
            return Result.error("用户名已存在");
        } else {
            userServiceImpl.register(username, password);
            return Result.success();
        }
    }

    @PostMapping("/login")
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
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader("Authorization" ) String token*/) {
       /* Map<String, Object> map = JwtUtils.parseJWT(token);
        String username = (String) map.get("username");*/
        //获取当前登录用户的id
        Map<String,Object> map= ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userServiceImpl.findByUserName(username);
        return Result.success(user);
    }
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userServiceImpl.update(user);
        return Result.success();
    }
    @PatchMapping("/avatar")
   public Result updateAvatar(@RequestParam @URL String avatar) {
        userServiceImpl.updateAvatar(avatar);
        return Result.success();
   }
   @PatchMapping("/updatePwd")
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

}



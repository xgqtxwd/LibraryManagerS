package com.xu.utils;

import com.xu.pojo.Result;
import com.xu.pojo.User;
import com.xu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class IsAdminUtil {

    private final UserService userServiceImpl;

    // 构造器注入UserService
    @Autowired
    public IsAdminUtil(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    // 非静态方法，因为静态方法不能访问非静态字段
    public Boolean isAdminUser() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        User user = userServiceImpl.findByUserId(id);
        return user != null && user.getPrivilegeLevel() == 1;
    }
}

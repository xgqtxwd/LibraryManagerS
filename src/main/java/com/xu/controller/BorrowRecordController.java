package com.xu.controller;

import com.xu.pojo.BorrowRecords;
import com.xu.pojo.ReminderRequest;
import com.xu.pojo.Result;
import com.xu.pojo.User;
import com.xu.service.BorrowRecordService;
import com.xu.service.EmailService;
import com.xu.service.UserService;
import com.xu.service.impl.UserServiceImpl;
import com.xu.utils.ThreadLocalUtil;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/home/admin")
public class BorrowRecordController {
//
    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private BorrowRecordService borrowRecordService;// 注入服务层接口
    @Autowired
    private EmailService emailService;
    // 获取逾期记录

    @GetMapping("/overdue")
    public Result<List<BorrowRecords>> getOverdueRecords() {
        List<BorrowRecords> overdueRecords = borrowRecordService.getOverdueRecords();
        return Result.success(overdueRecords);
    }
    @GetMapping("/personal-overdue")
    public Result<List<BorrowRecords>> getOverdueRecordsByPersonId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer personId= (Integer) map.get("id");
        List<BorrowRecords> overdueRecords = borrowRecordService.getOverdueRecordsById(personId);
        return Result.success(overdueRecords);
    }

    @PostMapping("/send-reminder")
    public Result sendReminder(@RequestBody ReminderRequest request) {
        try {
            String email = getEmailByUserId(request.getUserId());
            log.info("Email: {}", email);
            // 假设有一个方法可以通过用户ID获取邮箱
            String subject = "逾期提醒";
            String text = "您借阅的图书已逾期，请尽快归还。";
            emailService.sendOverdueReminder(email, subject, text);
            return Result.success();
        } catch (MessagingException e) {
            return Result.error("发送邮件失败");
        }
    }

    private String getEmailByUserId(Integer userId) {
        User user = userServiceImpl.findByUserId(userId);
        return user.getEmail();
    }
}

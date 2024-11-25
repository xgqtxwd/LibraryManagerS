package com.xu.pojo;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private Integer id;
    private String newPassword;
}

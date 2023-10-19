package com.yyandywt99.webserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Yangyang
 * @create 2023-09-12 13:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class user {
    private int nameId;
    private String name;
    private String password;
    private String userText;
    private String userImage;
    private int credit;
    private LocalDateTime registerTime;
    private LocalDateTime updateTime;

}

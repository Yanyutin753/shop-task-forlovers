package com.yyandywt99.webserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Yangyang
 * @create 2023-09-23 11:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class produce {
    private Integer produceId;
    private Integer founderId;
    private String produceName;
    private String produceText;
    private String produceImage;
    private Integer produceCredit;
    private Integer produceNum;
    private boolean checkProduce;
    private String customer;
    private boolean checkLove;
    private LocalDateTime updateTime;
    private LocalDateTime registerTime;
}

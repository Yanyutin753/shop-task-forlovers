package com.yyandywt99.webserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Yangyang
 * @create 2023-09-24 20:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class room {
    private Integer produceId;
    private Integer founderId;
    private String produceName;
    private String produceText;
    private String produceImage;
    private Integer produceNum;
    private boolean checkProduce;
    private String useName;
    private LocalDateTime updateTime;
    private LocalDateTime registerTime;
}

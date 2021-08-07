package com.weimengchao.common.http;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weimengchao.common.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Builder
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = 2255595423912987288L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * 返回数据
     */
    private T data;

    public RestResult(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public RestResult(ResponseCode responseCode, T t) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.timestamp = LocalDateTime.now();
        this.data = t;
    }

}

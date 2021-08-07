package com.weimengchao.auth.config;

import com.weimengchao.common.constant.ResponseCode;
import com.weimengchao.common.http.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Auth2ResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<RestResult> translate(Exception e) throws Exception {
        log.error("Auth2异常e:{}", e);
        ResponseEntity<RestResult> responseEntity = new ResponseEntity<>(new RestResult<>(ResponseCode.UNAUTHORIZED), HttpStatus.OK);
        return responseEntity;
    }

}

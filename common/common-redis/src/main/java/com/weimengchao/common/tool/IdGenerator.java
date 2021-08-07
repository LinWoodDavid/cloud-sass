package com.weimengchao.common.tool;

import com.weimengchao.common.constant.TableType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IdGenerator {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private final Long startNO = 100000000L;

    /**
     * 获取唯一Id
     *
     * @param tableType
     * @return
     */
    public Long nextId(TableType tableType) {
        String key = new StringBuilder().append("UNIQUE_ID")
                .append("-")
                .append(tableType.getTableName())
                .toString();
        Long increment = stringRedisTemplate.opsForValue().increment(key);
        return increment + startNO;
    }

}

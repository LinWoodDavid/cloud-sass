package com.weimengchao.common.lock;

import com.weimengchao.common.exception.GetLockTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 */
@Slf4j
@Service
public class RedisLocker {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${releaseTime:60}")
    Integer releaseTime;//防死锁释放时间(单位s)
    @Value("${sleepMillis:100}")
    Long sleepMillis;//睡眠毫秒数

    /**
     * 批量加锁
     *
     * @param keys
     * @param milliseconds
     * @return
     */
    public boolean lock(List<String> keys, long milliseconds) {
        List<String> lockedKeys = new ArrayList<>();
        long timeout = System.currentTimeMillis() + milliseconds;
        boolean lockStatus = false;
        try {
            for (String key : keys) {
                lock(key, timeout - System.currentTimeMillis());
                lockedKeys.add(key);
                lockStatus = true;
            }
            return lockStatus;
        } catch (GetLockTimeoutException e) {
            log.error("获取锁超时;keys:{},lockedKeys:{},e:{}", keys, lockedKeys, e);
            if (lockStatus) {
                //获取锁超时释放已加锁
                for (String lockedKey : lockedKeys) {
                    unLock(lockedKey);
                }
            }
            return false;
        }
    }

    /**
     * 加锁
     *
     * @param key
     * @param milliseconds 获取锁超时时间
     * @return
     */
    public boolean lock(String key, long milliseconds) throws GetLockTimeoutException {
        boolean lock = lock(key, milliseconds, releaseTime);
        if (lock) {
            return lock;
        } else {
            throw new GetLockTimeoutException("获取锁超时; key:" + key);
        }
    }

    /**
     * 加锁
     *
     * @param key
     * @param timeOutMilliseconds
     * @return
     */
    public boolean locked(String key, long timeOutMilliseconds) {
        return lock(key, timeOutMilliseconds, releaseTime);
    }

    /**
     * 加锁
     *
     * @param key          key值
     * @param milliseconds 获取锁超时时间
     * @param releaseTime  防死锁释放时间(秒)
     * @return
     */
    public boolean lock(String key, long milliseconds, long releaseTime) {
        long expireTime = System.currentTimeMillis() + milliseconds;//获得锁超时时间
        boolean b = stringRedisTemplate.opsForValue().setIfAbsent(key, String.valueOf(expireTime), releaseTime, TimeUnit.SECONDS);
        if (b) {//加锁成功
            return true;
        } else {//未加锁
            //轮训加锁
            while (expireTime > System.currentTimeMillis()) {
                b = stringRedisTemplate.opsForValue().setIfAbsent(key, String.valueOf(expireTime), releaseTime, TimeUnit.SECONDS);
                if (b) {
                    return true;
                }
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //加锁超时返回
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @param key
     * @return
     */
    public boolean unLock(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * redis 事务demo
     *
     * @param key     key
     * @param value   value
     * @param timeout
     * @return
     */
    private Boolean redisTransaction(String key, String value, long timeout, TimeUnit unit) {
        //创建会话
        SessionCallback<Boolean> sessionCallback = new SessionCallback<Boolean>() {
            List<Object> exec = null;

            @Override
            @SuppressWarnings("unchecked")
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                stringRedisTemplate.opsForValue().setIfAbsent(key, value);
                stringRedisTemplate.expire(key, timeout, unit);
                exec = operations.exec();
                if (exec.size() > 0) {
                    return (Boolean) exec.get(0);
                }
                return false;
            }
        };
        return stringRedisTemplate.execute(sessionCallback);
    }

    /**
     * 该方法用于请求频率限制
     * 例如：某平台店铺接口限制频率为20次/s 那么maxCount=20,expire=1
     *
     * @param key      唯一值（平台、店铺、接口），根据接口访问限制级别不同
     * @param maxCount 最大请求次数
     * @param expire   时间(单位秒)
     * @throws InterruptedException 线程异常
     */
    public void await(String key, Long maxCount, long expire) throws InterruptedException {
        boolean check = false;
        while (!check) {
            check = check(key, maxCount, expire);
            if (check) {//没超过频率
                break;
            } else {//超过频率限制
                //睡眠
                Thread.sleep(sleepMillis);
            }
        }
    }

    //检查是否超过请求频率
    public boolean check(String key, Long maxCount, long expire) {
        //自增
        Long count = stringRedisTemplate.opsForValue().increment(key);
        if (count == 1) {//失效后第一次自增
            //设置过期时间
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            return true;
        } else if (count <= maxCount) {//小于最大次数
            return true;
        } else {//大于最大次数
            Long expireTime = stringRedisTemplate.getExpire(key);
            if (expireTime == -1) {//未设置过期时间
                //设置过期时间防止死锁
                stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
            return false;
        }
    }

}

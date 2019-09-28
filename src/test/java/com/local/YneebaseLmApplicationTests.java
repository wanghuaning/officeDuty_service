package com.local;

import com.local.common.redis.util.RedisUtil;
import com.local.util.VerifyCodeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YneebaseLmApplicationTests {

    @Resource
    private RedisUtil redisUtil;
    @Test
    public void contextLoads() {
        String code=VerifyCodeUtils.generateVerifyCode(4);
        redisUtil.set("code",code);
        System.out.println(code);
    }


}

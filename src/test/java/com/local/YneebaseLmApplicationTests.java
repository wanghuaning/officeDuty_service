package com.local;

import com.local.util.VerifyCodeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YneebaseLmApplicationTests {

    @Test
    public void contextLoads() {
        String code=VerifyCodeUtils.generateVerifyCode(4);
//        redisUtil.set("code",code);
        System.out.println(code);
    }


}

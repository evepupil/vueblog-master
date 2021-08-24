package com.markerhub;

import com.markerhub.util.COSClientUtil;
import com.markerhub.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
class VueblogApplicationTests {
@Autowired
RedisUtil r;
    @Test
    void contextLoads() {
        r.set("blogIdMax",new Integer(35));
        Integer res= (Integer) r.get("blogIdMax");
    System.out.println(res);
    }

}

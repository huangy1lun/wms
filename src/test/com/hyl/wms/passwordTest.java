package com.hyl.wms;


import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class passwordTest {

    @Test
    public void testConfigTools() throws Exception {
        String pwd = ConfigTools.encrypt("admin");
        System.out.println(pwd);
    }
}

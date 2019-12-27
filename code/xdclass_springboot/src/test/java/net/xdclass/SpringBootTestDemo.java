package net.xdclass;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XdclassApplication.class  //执行单元测试时，启动Application
        ,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootTestDemo {

    /**
     * run as junit
     */
    @Test
    public void testOne(){
        //Assert.assertEquals(1,1);
        TestCase.assertEquals(1,1);
        System.out.println("执行测试：Test OK");
    }

    /**
     * 执行测试之前，执行
     */
    @Before
    public void testBefter(){
        System.out.println("执行测试之前，执行");
    }

    /**
     * 执行测试之后，执行
     */
    @After
    public void testAfter(){
        System.out.println("执行测试之后，执行");
    }

}

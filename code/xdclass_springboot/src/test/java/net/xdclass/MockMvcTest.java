package net.xdclass;


import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XdclassApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc   //这个注释，非常重要
public class MockMvcTest {

    /**
     * 注入 MockMvc对象
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testApi() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/test_properties"))
                //.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        //

        int status = result.getResponse().getStatus();
        TestCase.assertEquals(200,status);
        System.out.println("测试完成");
    }

}
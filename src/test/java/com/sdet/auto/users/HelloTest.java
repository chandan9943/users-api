package com.sdet.auto.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void tc0001_helloWorld() throws Exception {
        mvc.perform(get("/helloworld"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, welcome to my users-api!"));
    }

    @Test
    public void tc0002_home() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, welcome to sdetAutomation's springboot app.  " +
                        "To see the swagger page please add \"/swagger-ui.html\" to the end of the url path!"));
    }
}
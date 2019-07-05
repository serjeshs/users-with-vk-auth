package com.serjeshs.usersvk.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PagesController.class)
@WithMockUser(
        username = "user"
)
@ContextConfiguration(classes = { PagesController.class })
public class PagesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void indexPage() throws Exception {
        mvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Основное меню")))
                .andExpect(view().name("index"));
    }

    @Test
    public void userManagementPage() throws Exception {
        mvc.perform(get("/user-management"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Управление пользователями")))
                .andExpect(view().name("user-management"));
    }
}
package com.general;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {APITest.class})
@ContextConfiguration(classes = {Main.class})
public class APITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_a_Create() throws Exception {

        this.mockMvc.perform(post("/Gods/humans/create/human111").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("human111")));

        this.mockMvc.perform(post("/Gods/humans/create/human222").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("human222")));
    }

    @Test
    public void test_b_Get() throws Exception {

        this.mockMvc.perform(get("/Gods/humans"))
                .andDo(print())
                .andExpect(content().string(containsString("human111")));
    }

    @Test
    public void test_c_Update() throws Exception {

        this.mockMvc.perform(post("/Gods/humans/update").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "        \"humanID\": 1,\n" +
                        "        \"humanName\": \"update\",\n" +
                        "        \"gods\": [\n" +
                        "            {\n" +
                        "                \"godID\": 1,\n" +
                        "                \"godName\": \"update\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }"))
                .andDo(print())
                .andExpect(content().string(containsString("update")));

    }

    @Test
    public void test_d_Delete() throws Exception {

        this.mockMvc.perform(delete("/Gods/humans/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_e_Get() throws Exception {

        this.mockMvc.perform(get("/Gods/humans/1"))
                .andDo(print())
                .andExpect(content().string(containsString("update")));
    }






    @Test
    public void test_f_Create() throws Exception {

        this.mockMvc.perform(post("/Gods/create/god1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("god1")));

        this.mockMvc.perform(post("/Gods/create/god2").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("god2")));
    }

    @Test
    public void test_g_Get() throws Exception {

        this.mockMvc.perform(get("/Gods"))
                .andDo(print())
                .andExpect(content().string(containsString("god1")));
    }

    @Test
    public void test_h_Update() throws Exception {

        this.mockMvc.perform(post("/Gods/update").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "        \"godID\": 1,\n" +
                        "        \"godName\": \"update\",\n" +
                        "        \"humans\": [\n" +
                        "            {\n" +
                        "                \"humanID\": 1,\n" +
                        "                \"humanName\": \"update\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }"))
                .andDo(print())
                .andExpect(content().string(containsString("update")));
    }

    @Test
    public void test_i_Delete() throws Exception {

        this.mockMvc.perform(delete("/Gods/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_j_Get() throws Exception {

        this.mockMvc.perform(get("/Gods/1"))
                .andDo(print())
                .andExpect(content().string(containsString("update")));
    }

}
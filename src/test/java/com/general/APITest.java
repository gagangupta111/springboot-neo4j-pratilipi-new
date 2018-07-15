package com.general;

import com.general.nodes.EdgePercentage;
import com.general.nodes.Story;
import com.general.nodes.User;
import com.google.gson.Gson;
import org.junit.Assert;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private static final int REPITITIONS = 10;

    @Test
    public void test_a_DeleteAllExisting() throws Exception {

        this.mockMvc.perform(delete("/general/deleteAll"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_b_CreateUsers() throws Exception {

        for (int i = 0; i < REPITITIONS; i++ ){
            this.mockMvc.perform(post("/general/user").contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"name\": \"User" + i + "\"\n" +
                            "}"))
                    .andExpect(content().string(containsString("User" + i )));
        }
    }

    @Test
    public void test_c_CreateStories() throws Exception {

        for (int i = 0; i < REPITITIONS; i++ ){
            this.mockMvc.perform(post("/general/story").contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"name\": \"Story" + i +"\",\n" +
                            "    \"isbn\": \"978-4-16-148410-0\"\n" +
                            "}"))
                    .andExpect(content().string(containsString("Story" + i )));
        }
    }

    @Test
    public void test_d_Create_Relationships() throws Exception {

        for (int i = 0; i < REPITITIONS; i++ ){
            for (int j = 0; j < REPITITIONS; j++ ) {
                this.mockMvc.perform(post("/general/save").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\t\"user\": {\n" +
                                "\t\t\"name\": \"User" + i + "\"\n" +
                                "\t},\n" +
                                "\t\"story\":{\n" +
                                "\t\t\"name\": \"Story" + j + "\"\n" +
                                "\t},\n" +
                                "    \"readPercentage\": \"81\"\n" +
                                "}"))
                        .andExpect(content().string(containsString("User" + i)))
                        .andExpect(content().string(containsString("Story" + j)));
            }
        }
    }

    @Test
    public void test_e_Check_Size() throws Exception {

        Gson gson = new Gson();
        Set<User> userSet =
                gson.fromJson(this.mockMvc.perform(get("/general/users")).andReturn().getResponse().getContentAsString(),
                        HashSet.class);
        Assert.assertEquals(userSet.size(), REPITITIONS);

        Set<Story> storySet =
                gson.fromJson(this.mockMvc.perform(get("/general/stories")).andReturn().getResponse().getContentAsString(),
                        HashSet.class);
        Assert.assertEquals(storySet.size(), REPITITIONS);

    }

    @Test
    public void test_f_Check_Size_Reads() throws Exception {

        Gson gson = new Gson();
        List<EdgePercentage> readsSet =
                gson.fromJson(this.mockMvc.perform(get("/general/reads")).andReturn().getResponse().getContentAsString(),
                        ArrayList.class);
        Assert.assertEquals(readsSet.size(), (REPITITIONS*REPITITIONS));

    }

}
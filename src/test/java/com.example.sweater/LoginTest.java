package com.example.sweater;

import com.example.sweater.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// The @SpringBootTest annotation tells Spring Boot to look for a
//main configuration class (one with @SpringBootApplication, for
//instance) and use that to start a Spring application context.
@RunWith(SpringRunner.class) // It`s junit framework annotation, that performs testing
//@RunWith annotation contains runner 'SpringRunner.class' that starts tests
@SpringBootTest
@AutoConfigureMockMvc // Spring will try to create class structure automatically, to replace MVC layer
@TestPropertySource("/application-test.properties")
public class LoginTest {
    // @Test annotation notices all tests methods from testcase class

    // Spring interprets the @Autowired annotation, and the controller is injected
    //before the test methods are run. We use AssertJ (which provides assertThat()
    //and other methods) to express the test assertions.
    @Autowired
    private MainController controller;

    // Another useful approach is to not start the server at all but to test only the layer
    //below that, where Spring handles the incoming HTTP request and hands it off to your
    //controller. That way, almost of the full stack is used, and your code will be called
    //in exactly the same way as if it were processing a real HTTP request but without the
    //cost of starting the server. To do that, use Spring’s MockMvc and ask for that to be
    //injected for you by using the @AutoConfigureMockMvc annotation on the test case.
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void test() throws Exception {
//        assertThat(controller).isNotNull();
//    }

    //in this test we, through replaced web layer(on mockMvc) doing request on main page and expects some result
    @Test
    public void contextLoad() throws Exception {
        this.mockMvc.perform(get("/")) //in perform() we want to perform Get request on "/" (main page of our project)
                .andDo(print()) //print(): show result in console
                .andExpect(status().isOk()) // we expect that status of http query will be 200(ok)
                .andExpect(content().string(containsString("Hello, guest"))) //we expect some kind of content, and expect that is contain substring("Hello, guest")
                .andExpect(content().string(containsString("Please, Login"))); //we expect some kind of content, and expect that is contain substring("Please, login")
    }

    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    //test user authentication
    @Test
    // @Sql is used to annotate a test class or test method to configure SQL
    //scripts to be run against a given database during integration tests.
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //use sql script before test methods
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) //use sql script after test methods
    public void correctLoginTest() throws Exception {
        this.mockMvc.perform(formLogin().user("u1").password("1"))//formLogin(): looks how we determined login page in context, and causes request to this page
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCredentials() throws Exception {
        this.mockMvc.perform(post("/login").param("user", "Alex"))
                .andDo(print())
                .andExpect(status().isForbidden());//isForbiden() == 403
    }

}

package com.example.sweater;

import com.example.sweater.controller.MessageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

// The @SpringBootTest annotation tells Spring Boot to look for a
//main configuration class (one with @SpringBootApplication, for
//instance) and use it to start a Spring application context.
@RunWith(SpringRunner.class) // It`s junit framework annotation, that performs testing;
//@RunWith annotation contains runner 'SpringRunner.class' that starts tests
@SpringBootTest
@AutoConfigureMockMvc // Spring will try to create class structure automatically, to replace MVC layer
@WithUserDetails("u1") // we give name of user which take part in authentication
// @TestPropertySource is a class-level annotation that you can
//use to configure the locations of properties files and inlined
//properties to be added to the set of PropertySources in the
//Environment for an ApplicationContext loaded for an integration test.
@TestPropertySource("/application-test.properties")
// @Sql is used to annotate a test class or test method to configure SQL
//scripts to be run against a given database during integration tests.
@Sql(value = {"/create-user-before.sql", "/message-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //use sql script before test methods
@Sql(value = {"/message-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) //use sql script after test methods
public class MainControllerTest {
    // Spring interprets the @Autowired annotation, and the controller is injected
    //before the test methods are run. We use AssertJ (which provides assertThat()
    //and other methods) to express the test assertions.
    @Autowired
    private MessageController controller;

    // Another useful approach is to not start the server at all but to test only the layer
    //below that, where Spring handles the incoming HTTP request and hands it off to your
    //controller. That way, almost of the full stack is used, and your code will be called
    //in exactly the same way as if it were processing a real HTTP request but without the
    //cost of starting the server. To do that, use Spring’s MockMvc and ask for that to be
    //injected for you by using the @AutoConfigureMockMvc annotation on the test case.
    @Autowired
    private MockMvc mockMvc;

    // !!! before authentication test, user must be authenticated -> for this purpose @WithUserDetails  annotation used with class !!!
    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated()) //correct authentication check
                .andExpect(xpath("//div[@id='navbarSupportedContent']/div").string("u1")); //we expect "u1" string at xpath() (XPath - query language for xml documents)
    }

    //to make this test correct, we need use another database that will be used for all tests
    @Test //test correctness of message list display
    public void messageListTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(4)); //we expect that we will obtain certain count of node on page (in our sql scripts we have 4 messages, so count is 4)
    }

    @Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/main").param("filter", "my-tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(2)) //we expect that we will obtain certain count of node on page with "my-tag" tag (in our sql scripts we have 4 messages, two of which have got "my-tag" tag, so count is 2)
                .andExpect(xpath("//div[@id='message-list']/div[@data-id=1]").exists()) //we expect to obtain message that will have data-id attribute equal to 1
                .andExpect(xpath("//div[@id='message-list']/div[@data-id=3]").exists());
    }

    @Test
    public void addMessageToListTest() throws Exception {
        //we should use multipart(), rather than post() because we use MultipartFile in  post request (in add() in MainController)
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("file", "123".getBytes())
                .param("text", "fifth")  // one of necessary fields of Message class
                .param("tag", "new one") // one of necessary fields of Message class
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(5)) //we expect that we will obtain certain count of node on page with "my-tag" tag (in our sql scripts we have 4 messages, two of which have got "my-tag" tag, so count is 2)
                .andExpect(xpath("//div[@id='message-list']/div[@data-id=10]").exists()) //we expect to obtain message that will have data-id attribute=10(10:due to the script)
                .andExpect(xpath("//div[@id='message-list']/div[@data-id=10]/div/span").string("fifth")) //we expect to obtain message that will have data-id attribute=10 and  text="fifth"
                .andExpect(xpath("//div[@id='message-list']/div[@data-id=10]/div/i").string("#new one")); //we expect to obtain message that will have data-id attribute=10 and tag="new one"
    }
}

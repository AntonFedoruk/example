package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

// In Spring’s approach to building web sites, HTTP requests are handled by a controller.
// You can easily identify the controller by the @Controller annotation.
// GreetingController handles GET requests for /greeting by returning the name of a View (in this case, greeting).
// A View is responsible for rendering the HTML content.
@Controller // This means that this class is a Controller
//@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called messageRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MessageRepository messageRepository;

    // The @GetMapping annotation ensures that HTTP GET requests to /greeting are mapped to the greeting() method.
    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        System.out.println("in greeting method");
        return "greeting";
    }
    /*
        @RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method.
        This query string parameter is not required. If it is absent in the request, the defaultValue of World is used.
        The value of the name parameter is added to a Model object, ultimately making it accessible to the view template.
     */

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    //Map ONLY POST Requests
    @PostMapping("/main")   // @RequestParam means it is a parameter from the GET or POST request
    public String add(
            @AuthenticationPrincipal User user, //we get the current user as a parameter
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag, user);
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        model.put("filter", "");
        return "main";
    }
}

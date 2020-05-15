package com.example.sweater;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// In Spring’s approach to building web sites, HTTP requests are handled by a controller.
// You can easily identify the controller by the @Controller annotation.
// GreetingController handles GET requests for /greeting by returning the name of a View (in this case, greeting).
// A View is responsible for rendering the HTML content.
@Controller
public class GreetingController {

    // The @GetMapping annotation ensures that HTTP GET requests to /greeting are mapped to the greeting() method.
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }
    /*
        @RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method.
        This query string parameter is not required. If it is absent in the request, the defaultValue of World is used.
        The value of the name parameter is added to a Model object, ultimately making it accessible to the view template.
     */

    @GetMapping
    public String main(Map<String, Object> model){
        model.put("some", "Anton, YOU MUST CODING!!!");
        return "main";
    }

}

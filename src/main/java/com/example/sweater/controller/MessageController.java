package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repository.MessageRepository;
import com.example.sweater.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// In Spring’s approach to building web sites, HTTP requests are handled by a controller.
// You can easily identify the controller by the @Controller annotation.
// GreetingController handles GET requests for /greeting by returning the name of a View (in this case, greeting).
// A View is responsible for rendering the HTML content.
@Controller // This means that this class is a Controller
//@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MessageController {
    @Autowired // This means to get the bean called messageRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    //with @Value("SpEL") annotation we tell Spring that we want to get variable.
    @Value("${upload.path}") //In this case we will get 'upload.path' from application.properties
    private String uploadPath;

    // The @GetMapping annotation ensures that HTTP GET requests to /greeting are mapped to the greeting() method.
    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }
    /*
        @RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method.
        This query string parameter is not required. If it is absent in the request, the defaultValue of World is used.
        The value of the name parameter is added to a Model object, ultimately making it accessible to the view template.
     */

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model,
                       @AuthenticationPrincipal User user, //we get the current user as a parameter from session
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) { //Pageable serve for page-by-page display of long lists(is also added to MessageRepository);
        // sort param show type of sorting(in our case: sorting by "id"); direction param describe direction of sort(in our case: first of all we will display latest messages added
        // @PageableDefault needed to prevent mistakes
        Page<MessageDto> page = messageService.messageList(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        return "main";
    }

    //Map ONLY POST Requests
    @PostMapping("/main")   // @RequestParam means it is a parameter from the GET or POST request
    public String add(
            @AuthenticationPrincipal User user, //we get the current user as a parameter from session
            @Valid Message message, //@Valid start validation; due to the fact that we begin validation we must add BindingResult
//          !!!THIS('BindingResult') ARGUMENT MUST STAY BEFORE 'MODEL' ARGUMENT, to prevent their representation in view!!!
            BindingResult bindingResult, //list of arguments and messages about validation`s errors
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap); //add to view
            model.addAttribute("message", message);
        } else { //validation completed without errors
            saveFile(message, file);

            //we need to delete our message from  model, because in order of successful message adding we will get open form with recently added data
            model.addAttribute("message", null);

            messageRepository.save(message);
        }
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("filter", "");
        model.addAttribute("url", "/main");
        return "main";
    }

    private void saveFile(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            //check directory availability
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            //create a unique file name to protect against collisions
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            //file uploading
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-messages/{author}")
    public String userMessages(@AuthenticationPrincipal User currentUser, //we get the current user as a parameter from session
                               @PathVariable User author, //user that we want to receive(we may omit annotation param name="user", as out User variable has got the same name)
                               Model model,
                               @RequestParam(required = false) Message message,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) { //Pageable serve for page-by-page display of long lists(is also added to MessageRepository);
        // sort param show type of sorting(in our case: sorting by "id"); direction param describe direction of sort(in our case: first of all we will display latest messages added
        // @PageableDefault needed to prevent mistakes
        Page<MessageDto> page = messageService.messageListForUser(pageable, currentUser, author);

        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("userChannel", author);
        model.addAttribute("page", page);
        model.addAttribute("messages", message);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("url", "/user-messages/" + author.getId());

        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(@AuthenticationPrincipal User currentUser, //we get the current user as a parameter from session
                                @PathVariable Long user, //user that we want to receive(we may omit annotation param name="user", as out User variable has got the same name)
                                @RequestParam("id") Message message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }
            saveFile(message, file);
            messageRepository.save(message);
        }
        return "redirect:/user-messages/" + user;
    }

    @GetMapping("/messages/{message}/like")
    public String like(@AuthenticationPrincipal User currentUser, //we get the current user as a parameter from session
                       @PathVariable Message message,
                       RedirectAttributes redirectAttributes, //parameter, that allow us to throw some arguments in method, that we chose redirect to(this param takes from param of current method)
                       @RequestHeader(required = false) String referer) { //@RequestHeader gave ot us a page, from which we have came with like
        Set<User> likes = message.getLikes();

        //adding or removing likes
        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        //extracting parameters from current method
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
        //thus we  transfer all obtained attributes(pagination param, current location on page and etc. ) as parameter to redirected method

        //we should to redirect user to the same page, where he came from
        return "redirect:" + components.getPath();
    }
}
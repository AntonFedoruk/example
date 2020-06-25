package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.UserRepository;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("message", "");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("password2") String passwordConfirmation,
                          @Valid User user, //@Valid start validation; due to the fact that we begin validation we must add BindingResult
                          // !!!THIS('BindingResult') ARGUMENT MUST STAY BEFORE 'MODEL' ARGUMENT, to prevent their representation in view!!!
                          BindingResult bindingResult, //list of arguments and messages about validation`s errors
                          Model model) {
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirmation);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation can`t be empty");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Passwords are different");
        }

        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration"; //to prevent saving of invalid user
        }

        if(!userService.addUser(user)){
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
    return "redirect:/login";
    }

    @GetMapping("activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated!");
        } else {

            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }
}

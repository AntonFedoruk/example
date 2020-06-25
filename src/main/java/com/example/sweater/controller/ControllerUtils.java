package com.example.sweater.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {

    //it is static because we don't use this method anywhere else, except in controllers
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",//as key we will use name with "Error" ends
                FieldError::getDefaultMessage); //value of this Map
        return bindingResult.getFieldErrors().stream().collect(collector);
    }
}

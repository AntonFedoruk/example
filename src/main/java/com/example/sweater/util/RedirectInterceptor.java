package com.example.sweater.util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//with using Turbolinks it is necessary to add "Turbolinks-Location" to redirection for correct displaying of URL after redirections,
//so we use HandlerInterceptorAdapter postHandle() to complete this tusk
public class RedirectInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            //add header to response attribute
            String args = request.getQueryString() != null ? request.getQueryString() : ""; //its arguments that are located in URL after '?'
            String url = request.getRequestURI().toString() + "?" + args;
            response.setHeader("Turbolinks-Location", url);
        }
    }
}

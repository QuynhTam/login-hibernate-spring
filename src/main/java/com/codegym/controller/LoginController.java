package com.codegym.controller;

import com.codegym.model.Users;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("login");
        Users loginBean = new Users();
        modelAndView.addObject("loginBean", loginBean);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginBean") Users loginBean) {
        ModelAndView modelAndView = null;
        if (userService.isUsers(loginBean.getUsername(), loginBean.getPassword())) {
            request.setAttribute("loggedInUser", loginBean.getUsername());
            modelAndView = new ModelAndView("welcome");
        } else {
            request.setAttribute("message", "Invalid ussename or password!");
            modelAndView = new ModelAndView("login");
        }
        return modelAndView;
    }
}

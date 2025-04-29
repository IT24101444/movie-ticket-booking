package com.movieticket.controller;

import com.movieticket.model.User;
import com.movieticket.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

@Controller
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping("/register")
    public String registerPage() {
        return "redirect:/register.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password) || !StringUtils.hasText(email)) {
            return "redirect:/register.html?error=empty";
        }
        try {
            if (userService.findUserByUsername(username) != null) {
                return "redirect:/register.html?error=exists";
            }
            userService.saveUser(new User(username, password, email));
        } catch (Exception e) {
            return "redirect:/register.html?error=io";
        }
        return "redirect:/login.html?success=registered";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/login.html";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password) {
        try {
            User user = userService.findUser(username, password);
            if (user != null) {
                return "redirect:/profile.html?username=" + user.getUsername() + "&email=" + user.getEmail();
            } else {
                return "redirect:/login.html?error=invalid";
            }
        } catch (Exception e) {
            return "redirect:/login.html?error=io";
        }
    }
}

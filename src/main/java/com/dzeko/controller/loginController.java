package com.dzeko.controller;

import com.dzeko.model.User;
import com.dzeko.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
public class loginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String login(HttpSession session) {
        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }
        return "order";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {

        // Validación básica de entrada
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", true);
            return "login";
        }

        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            session.setAttribute("userId", user.iduser());
            session.setAttribute("role", user.idRole());
            return "redirect:/order";
        } else {
            model.addAttribute("error", true);
            return "login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}

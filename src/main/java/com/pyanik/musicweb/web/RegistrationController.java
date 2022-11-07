package com.pyanik.musicweb.web;

import com.pyanik.musicweb.domain.user.UserService;
import com.pyanik.musicweb.domain.user.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        UserRegistrationDto userRegistration = new UserRegistrationDto();
        model.addAttribute("user", userRegistration);
        return "registration-form";
    }

    @PostMapping("/registration")
    public String register(UserRegistrationDto userRegistration) {
        userService.registerUserWithDefaultRole(userRegistration);
        return "redirect:/";
    }
}

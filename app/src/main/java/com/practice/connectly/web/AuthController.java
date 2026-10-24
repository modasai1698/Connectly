package com.practice.connectly.web;

import com.practice.connectly.model.AppUser;
import com.practice.connectly.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated()
                ? "redirect:/dashboard" : "redirect:/login";
    }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationForm") RegistrationForm form,
                           BindingResult result, Model model) {
        if (form.getPassword() != null && !form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match.");
        }
        if (form.getUsername() != null && users.existsByUsernameIgnoreCase(form.getUsername())) {
            result.rejectValue("username", "username.taken", "That username is already taken.");
        }
        if (form.getEmail() != null && users.existsByEmailIgnoreCase(form.getEmail())) {
            result.rejectValue("email", "email.taken", "An account with that email already exists.");
        }
        if (result.hasErrors()) return "register";

        try {
            users.save(new AppUser(
                    form.getUsername(),
                    form.getEmail().toLowerCase(),
                    passwordEncoder.encode(form.getPassword())
            ));
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("registrationError", "That username or email is already registered.");
            return "register";
        }
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "dashboard";
    }
}

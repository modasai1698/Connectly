package com.practice.connectly.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {
    @NotBlank(message = "Choose a username.")
    @Size(min = 3, max = 30, message = "Username must be 3–30 characters.")
    @Pattern(regexp = "^[A-Za-z0-9_.-]+$", message = "Use letters, numbers, dot, underscore or hyphen.")
    private String username;

    @NotBlank(message = "Enter your email.")
    @Email(message = "Enter a valid email address.")
    @Size(max = 254, message = "Email is too long.")
    private String email;

    @NotBlank(message = "Choose a password.")
    @Size(min = 8, max = 72, message = "Password must be 8–72 characters.")
    private String password;

    @NotBlank(message = "Confirm your password.")
    private String confirmPassword;

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setUsername(String username) { this.username = username == null ? null : username.trim(); }
    public void setEmail(String email) { this.email = email == null ? null : email.trim(); }
    public void setPassword(String password) { this.password = password; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}

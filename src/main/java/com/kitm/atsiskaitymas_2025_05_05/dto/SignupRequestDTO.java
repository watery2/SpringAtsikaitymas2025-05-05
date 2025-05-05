package com.kitm.atsiskaitymas_2025_05_05.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SignupRequestDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;


    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    public @NotBlank @Size(min = 3, max = 50) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 50) String username) {
        this.username = username;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public @NotBlank @Size(min = 6, max = 50) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6, max = 50) String password) {
        this.password = password;
    }
}

package com.stevy.contratti.payload.response;

import com.stevy.contratti.models.Role;
import com.stevy.contratti.models.Societa;

import java.util.List;
import java.util.Set;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private Set<Societa> societas;
    private Set<Role> role;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles,Set<Role> role, Set<Societa> societas) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.societas = societas;
        this.role = role;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Set<Societa> getSocietas() {
        return societas;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public void setSocietas(Set<Societa> societas) {
        this.societas = societas;
    }
}

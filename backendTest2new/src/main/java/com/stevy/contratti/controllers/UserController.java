package com.stevy.contratti.controllers;
import com.stevy.contratti.models.User;
import com.stevy.contratti.payload.response.MessageResponse;
import com.stevy.contratti.repository.ContratRepository;
import com.stevy.contratti.repository.UserRepository;
import com.stevy.contratti.service.ContraService;
import com.stevy.contratti.service.email.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PutMapping("/user/update")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZIONE') or hasRole('ROLE_GESTIONE')")
    public ResponseEntity < MessageResponse > updateuser(@Valid @RequestBody UserForm userForm) {
        User up = userService.updatePassword(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword(), userForm.getId_user());
        return ResponseEntity.ok(new MessageResponse("Update User", true, "User Updater successfully", up));
    }
}
@Data
class UserForm {
    private int id_user;
    private String username;
    private String password;
    private String confirmedPassword;
    public int getId_user() {
        return id_user;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getConfirmedPassword() {
        return confirmedPassword;
    }
}
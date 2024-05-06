package com.stevy.contratti.service.email;

import com.stevy.contratti.models.Contrat;
import com.stevy.contratti.models.Role;
import com.stevy.contratti.models.Societa;
import com.stevy.contratti.models.User;

import java.util.List;

public interface UserService {
    public User saveUser(String username, String password, String confirmedPassword);
    public User updatePassword(String username, String password, String confirmedPassword, int id);
    public Contrat userPriority(Contrat c, List<Societa> s, List<Role> r, User u);
}

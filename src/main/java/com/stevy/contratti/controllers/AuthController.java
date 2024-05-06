package com.stevy.contratti.controllers;

import com.stevy.contratti.models.*;
import com.stevy.contratti.payload.request.LoginRequest;
import com.stevy.contratti.payload.request.SignupRequest;
import com.stevy.contratti.payload.response.JwtResponse;
import com.stevy.contratti.payload.response.MessageResponse;
import com.stevy.contratti.repository.RoleRepository;
import com.stevy.contratti.repository.SocietaRepository;
import com.stevy.contratti.repository.UserRepository;
import com.stevy.contratti.security.jwt.JwtUtils;
import com.stevy.contratti.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SocietaRepository societaRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity < ? > authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List < String > roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles,userDetails.getRuolo(), userDetails.getSocietas()));
    }

    @PostMapping("/signup")
    public ResponseEntity < ? > registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        Set < String > strRoles = signUpRequest.getRole();
        Set < Role > roles = new HashSet < > ();

        if (strRoles == null) {
      /*Role hrRole = roleRepository.findByName(ERole.ROLE_HR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(hrRole);*/
            throw new RuntimeException("Error: role is not found.");
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "hr":
                        Role hrRole = roleRepository.findByName(ERole.ROLE_HR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(hrRole);

                        break;

                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMMINISTRAZION)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;

                    case "superadmin":
                        Role superadminRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(superadminRole);

                        break;
                    case "logistica":
                        System.out.println("ERole.ROLE_MODERATOR==" + ERole.ROLE_LOGISTICA);
                        Role modRole = roleRepository.findByName(ERole.ROLE_LOGISTICA)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        System.out.println("ERole.ROLE_MODERATOR==" + ERole.ROLE_LOGISTICA);
                        roles.add(modRole);

                        break;
                    case "gestione":
                        System.out.println("ERole.ROLE_MODERATOR==" + ERole.ROLE_GESTIONE);
                        Role gesRole = roleRepository.findByName(ERole.ROLE_GESTIONE)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        System.out.println("ERole.ROLE_MODERATOR==" + ERole.ROLE_GESTIONE);
                        roles.add(gesRole);

                        break;
          /* default:
              Role hrRole = roleRepository.findByName(ERole.ROLE_HR)
                      .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
              roles.add(hrRole);*/
                }
            });
        }

        Set<Societa> sc = this.listSociete(signUpRequest.getSocieta());
        user.setRoles(roles);
        user.setSocietas(sc);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    private Set<Societa> listSociete(Set<String> societa) {
        Set<String> strSocietas = societa;
        Set<Societa> societas = new HashSet<>();

        if (strSocietas == null) {
            /*Societa userSocieta = societaRepository.findByName(ESocieta.BE_CONSULTING)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            societas.add(userSocieta);*/
            throw new RuntimeException("Error: societa is not found.");
        } else {
            strSocietas.forEach(societ -> {
                switch (societ) {
                    case "BE_CONSULTING":
                        Societa soc1 = societaRepository.findByName(ESocieta.BE_CONSULTING)
                            .orElseThrow(() -> new RuntimeException("Error: Societa is not found."));
                        societas.add(soc1);
                        break;
                    case "BE_SOLUTIONS":
                        System.out.println("ESocieta.BE_SOLUTIONS=="+ESocieta.BE_SOLUTIONS);
                        Societa soc2 = societaRepository.findByName(ESocieta.BE_SOLUTIONS)
                            .orElseThrow(() -> new RuntimeException("Error: Societa is not found."));
                        societas.add(soc2);
                        break;
                    case "BE_BE":
                        System.out.println("ESocieta.BE_BE=="+ESocieta.BE_BE);
                        Societa soc3 = societaRepository.findByName(ESocieta.BE_BE)
                            .orElseThrow(() -> new RuntimeException("Error: Societa is not found."));
                        societas.add(soc3);
                        break;
                    case "IBE":
                        System.out.println("ESocieta.BE_BE=="+ESocieta.IBE);
                        Societa soc4 = societaRepository.findByName(ESocieta.IBE)
                            .orElseThrow(() -> new RuntimeException("Error: Societa is not found."));
                        societas.add(soc4);

                        break;
                    case "BE_SFCS":
                        System.out.println("ESocieta.BE_BE=="+ESocieta.BE_SFCS);
                        Societa soc5 = societaRepository.findByName(ESocieta.BE_SFCS)
                            .orElseThrow(() -> new RuntimeException("Error: Societa is not found."));
                        societas.add(soc5);
                        break;
                    default:
                       /* System.out.println("ESocieta.BE_BE=="+ESocieta.BE_SFCS);
                        Societa soc6 = societaRepository.findByName(ESocieta.BE_CONSULTING)
                                .orElseThrow(() -> new RuntimeException("Error: Societa is not found."));
                        societas.add(soc6);*/
                }
            });
        }
        return societas;
    }

}
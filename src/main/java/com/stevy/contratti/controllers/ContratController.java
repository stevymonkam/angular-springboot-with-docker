package com.stevy.contratti.controllers;

import com.stevy.contratti.models.*;
import com.stevy.contratti.payload.response.MessageResponse;

import com.stevy.contratti.repository.ContratRepository;
import com.stevy.contratti.repository.SocietaRepository;
import com.stevy.contratti.repository.UserRepository;
import com.stevy.contratti.security.services.UserDetailsImpl;
import com.stevy.contratti.service.ContraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class ContratController {
    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private ContraService contratService;

    @Autowired
    SocietaRepository societaRepository;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/contrat/create")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody Contrat contrat) {
        contratRepository.save(contrat);

        return ResponseEntity.ok(new MessageResponse("create contrat", "ok",true, "contrat created successfully",contratRepository.save(contrat)));
    }

    @PutMapping("/contrat/update")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody Contrat contrat) {
        Contrat contratExist =  contratService.update(contrat);
        String message = "";
        if (contratExist == null) {
            message = "Unauthorizes action, you are not authorize to make update";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("Update Contrat","error",false,message));
        }
        message = "Contrat updated successfully !";
        return ResponseEntity.ok(new MessageResponse("Update contrat", "ok",true, message, contratExist));
    }

    @DeleteMapping("/contrat/delete/{id}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public String Delete(@PathVariable(name = "id") Long id_contrat){
        System.out.println(id_contrat);
        contratRepository.deleteById(id_contrat);

        //contratRepository.deleteById((long) ct.getId());
        return "contrato delete con success";
    }

    @GetMapping("/contrat/show/{id}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> show(@PathVariable(name = "id") Long id_contrat) {
        Contrat ct = contratService.show(id_contrat);
        String message = "";
        if (ct == null) {
            message = "Unauthorizes action, you are not authorize to show this contrat";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("show Contrat","error",false,message));
        }
        return ResponseEntity.ok(new MessageResponse("Show contrat", "ok",true, "Show contrat successful",ct));
    }

    @GetMapping("/contrat/list")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> list() {
        if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_ADMMINISTRAZION") || AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_GESTIONE") || AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_SUPER_ADMIN")){
            List<Contrat> lst = (List<Contrat>) contratRepository.findAll();
            return ResponseEntity.ok(new MessageResponse("List contrat", "ok",true, "list of contrat",lst));
        } else {
            ERole role = AuthorityUtils.authorityListToSet(SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
            ).contains("ROLE_HR") ? ERole.ROLE_HR : ERole.ROLE_LOGISTICA;
            List<Contrat> lst = (List<Contrat>) contratRepository.findByRole(role);
            return ResponseEntity.ok(new MessageResponse("List contrat", "ok",true, "list of contrat",lst));
        }
    }
    @GetMapping("/GetListsignalisations")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity < MessageResponse > getListsignal() throws ParseException {

        if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_ADMMINISTRAZION") || AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_GESTIONE") || AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_SUPER_ADMIN")) {
            List<Contrat> lst = (List<Contrat>) contratRepository.findAll();
            List<Contrat> listsegnalisation = contratService.Segnalisazioni(lst);
            return ResponseEntity.ok(new MessageResponse("List segnalisation", "ok",true, "list of contrat",listsegnalisation));
        } else {
            ERole role = AuthorityUtils.authorityListToSet(SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
            ).contains("ROLE_HR") ? ERole.ROLE_HR : ERole.ROLE_LOGISTICA;
            List<Contrat> lst = (List<Contrat>) contratRepository.findByRole(role);
            List<Contrat> listsegnalisation = contratService.Segnalisazioni(lst);
            return ResponseEntity.ok(new MessageResponse("List segnalisation", "ok",true, "list of contrat",listsegnalisation));
        }
    }

    @PostMapping("contrat/interrogation/{tipo}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity < MessageResponse > interogation(@PathVariable(name = "tipo") String tipo, @Valid @RequestBody Interogation inter) {
        if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_ADMMINISTRAZION") || AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_GESTIONE")) {
            List < Contrat > lst = contratService.ListInterrogazione(tipo, inter.getDe(), inter.getA());
            return ResponseEntity.ok(new MessageResponse("List interogation", "ok", true, "filter successfully", lst));
        } else {
            ERole role = AuthorityUtils.authorityListToSet(SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
            ).contains("ROLE_HR") ? ERole.ROLE_HR : ERole.ROLE_LOGISTICA;
            List < Contrat > lst = contratService.ListInterrogazione(tipo, inter.getDe(), inter.getA()); // doit etre gere
            return ResponseEntity.ok(new MessageResponse("List interogation", "ok", true, "filter successfully", lst));
        }

    }

}

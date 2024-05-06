package com.stevy.contratti;

import com.stevy.contratti.models.*;

import com.stevy.contratti.repository.ContratRepository;
import com.stevy.contratti.repository.RoleRepository;
import com.stevy.contratti.repository.SocietaRepository;
import com.stevy.contratti.repository.UserRepository;
import com.stevy.contratti.service.ContraService;
import com.stevy.contratti.service.FileContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class DemoApplication {
    @Resource
    FileContratService fileContratService;

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SocietaRepository societaRepository;

    @Autowired
    private UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ContraService contraService) {
        return args->{
            /*fileContratService.deleteAll();
            fileContratService.init();
             //contraService.compareDate3(1l);
             //contraService.ListInterrogazione("2021-09-06","2021-09-06","2021-09-06");
             List<Contrat> l =  contratRepository.findAll();
             contraService.Segnalisazioni(l);*/




              for(int i=0; i<2; i++){

            if(i==0){
                Role r = new Role();
                r.setName(ERole.ROLE_HR);
                roleRepository.save(r);
            }
            if(i==1){
                Role r = new Role();
                r.setName(ERole.ROLE_ADMMINISTRAZION);
                roleRepository.save(r);
            }

        }



            for(int i=0; i<2; i++){

                if(i==0){
                    Societa s = new Societa();
                    s.setName(ESocieta.BE_SOLUTIONS);
                    societaRepository.save(s);
                }
                if(i==1){
                    Societa s = new Societa();
                    s.setName(ESocieta.BE_CONSULTING);
                    societaRepository.save(s);
                }

            }
            Set < Role > roles = new HashSet < > ();
            Set<Societa> societas = new HashSet<>();

                Role hrRole = roleRepository.findByName(ERole.ROLE_HR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(hrRole);


                Societa userSocieta = societaRepository.findByName(ESocieta.BE_CONSULTING)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                societas.add(userSocieta);


            User u = new User();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            u.setUsername("stevy");
            u.setPassword(encoder.encode("manounou"));
            u.setEmail("stevy@yahoo.fr");
            u.setRoles(roles);
            u.setSocietas(societas);
            userRepository.save(u);



        };
    }

}

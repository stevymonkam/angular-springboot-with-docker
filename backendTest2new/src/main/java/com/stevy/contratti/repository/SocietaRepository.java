package com.stevy.contratti.repository;

import com.stevy.contratti.models.ERole;
import com.stevy.contratti.models.ESocieta;
import com.stevy.contratti.models.Role;
import com.stevy.contratti.models.Societa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocietaRepository extends JpaRepository<Societa, Long> {
    Optional<Societa> findByName(ESocieta name);
}

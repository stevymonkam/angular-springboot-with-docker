package com.stevy.contratti.repository;


import com.stevy.contratti.models.FileContrat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileContratRepository extends JpaRepository<FileContrat, Long> {
    public FileContrat findFileContratById(Long id);
}

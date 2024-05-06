package com.stevy.contratti.repository;

import com.stevy.contratti.models.Contrat;
import com.stevy.contratti.models.ERole;
import com.stevy.contratti.models.FileContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface ContratRepository extends JpaRepository<Contrat, Long> {

    public  Contrat findContratById(Long id);
    @Async
    @Query("SELECT c as contrat, u as user FROM Contrat c " +
            "join User u ON c.user.id = u.id join u.roles ur where ur.name = :role_name ")
    List<Contrat> findByRole(@Param("role_name") ERole role_name);

    @Async
    @Query("SELECT c FROM Contrat c where c.societa.id = :id and c.id = :id_contrat")
    Contrat findBySocietaId(@Param("id") Integer id, @Param("id_contrat") Long id_contrat);

    @Async
    @Query("SELECT c as contrat, u as user FROM Contrat c " +
            "join User u ON c.user.id = u.id join u.roles ur where ur.name = :role_name and c.id = :id")
    Contrat findByUserRoleLogis(@Param("role_name") ERole role_name, @Param("id") Long id);
    @Async
    @Query("SELECT c  FROM Contrat c join c.fileContrats cf where cf.id = :id")
    Contrat findByFileContratsId(@Param("id") Long id);


    @Async
    @Query("SELECT c as contrat, u as user FROM Contrat c " +
            "join User u ON c.user.id = u.id join u.roles ur where ur.name = :role_name and c.id = :id")
    Contrat findByUserRoleAdmin(@Param("role_name") ERole role_name, @Param("id") Long id);

    @Async
    @Query("SELECT c as contrat, u as user FROM Contrat c " +
            "join User u ON c.user.id = u.id join u.roles ur where ur.name = :role_name and c.id = :id")
    Contrat findByRole(@Param("role_name") ERole role_name, @Param("id") Long id);

    @Async
    @Query(value = "SELECT c FROM Contrat c where c.data_scadenza>= ?1 and c.data_scadenza <= ?2")
    List<Contrat> findByData_scadenza(String data_scadenza1, String data_scadenza2);

    @Async
    @Query("SELECT c FROM Contrat c where c.data_disdetta >= ?1 and c.data_disdetta <= ?2")
    List<Contrat> findByData_disdetta(String data_disdetta1, String data_disdetta2);

    @Async
    @Query("SELECT c FROM Contrat c where c.data_validata >= ?1 and c.data_validata <= ?2")
    List<Contrat> findByData_validata(String data_validata1, String data_validata2);

    @Async
    @Query("SELECT m FROM Contrat m WHERE m.codice LIKE ?1%")
    List<Contrat> searchByRatingStartsWith(String rating);


}

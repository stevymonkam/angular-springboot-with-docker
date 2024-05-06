package com.stevy.contratti.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Società")
public class Societa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ESocieta name;

    @OneToMany(fetch= FetchType.EAGER)
    Set<Contrat> contrats;

    public Set<Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(Set<Contrat> contrats) {
        this.contrats = contrats;
    }

    public Societa() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ESocieta getName() {
        return name;
    }

    public void setName(ESocieta name) {
        this.name = name;
    }

}

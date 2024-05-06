package com.stevy.contratti.service;

import com.stevy.contratti.models.Contrat;

import java.text.ParseException;
import java.util.List;

public interface ContraService {

    public List<Contrat> Segnalisazioni(List<Contrat> l) throws ParseException;
    public  List<Contrat> ListInterrogazione(String data,String data1,String data2);
    public Contrat update(Contrat contrat );
    public Contrat show(Long id );
}

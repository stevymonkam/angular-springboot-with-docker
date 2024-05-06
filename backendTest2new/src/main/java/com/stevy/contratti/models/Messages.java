package com.stevy.contratti.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Messages {
    private String[] a = new String[] {};
    private String[] cc = new String[] {};
    private String oggetto;
    private String corpo;
    private String allegato;

    public Messages() {}

    public String getAllegato() {
        return allegato;
    }

    public void setAllegato(String allegato) {
        this.allegato = allegato;
    }

    public String[] getA() {
        return a;
    }

    public void setA(String[] a) {
        this.a = a;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }
}
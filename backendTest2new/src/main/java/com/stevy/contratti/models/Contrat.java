package com.stevy.contratti.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@ToString
public class Contrat {

    @Id
    @GeneratedValue
    private Long id;
    private int id_contrat;
    private String owner;
    private String sede;
    private String azienda;
    private String codice;
    private String fornitore;
    private String iva;
    private String lop_cliente;
    private String tipologia_contratto;
    private String stato_contratto;
    private String data_validata;
    private String rinnovo_automatico;
    private String data_disdetta;
    private String note;
    private boolean mail_preavviso;
    private boolean mail_contratto;
    private String data_rinnovo;
    private String periodo;
    private int preavviso;
    private String data_scadenza;
    private String created_at;
    private String updated_at;
    private String segnalazione;

    @Column(columnDefinition="TEXT")
    private String tipo_importo;

    /*
    @Column(columnDefinition="TEXT")
    private String valore_importo;
    */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "contrat_filecontrats",
            joinColumns = @JoinColumn(name = "contrat_id"),
            inverseJoinColumns = @JoinColumn(name = "filecontrat_id"))
    private Set<FileContrat> fileContrats = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_societa", referencedColumnName = "id")
    private Societa societa;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contrat(Long id) {
        this.id = id;
    }

    public Contrat() {
    }

    public Long getId() {
        return id;
    }

    public Societa getSocieta() {
        return societa;
    }

    public void setSocieta(Societa societa) {
        this.societa = societa;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSegnalazione() {
        return segnalazione;
    }

    public void setSegnalazione(String segnalazione) {
        this.segnalazione = segnalazione;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSede() {
        return sede;
    }


    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getTipo_importo() {
        return tipo_importo;
    }

    public void setTipo_importo(String tipo_importo) {
        this.tipo_importo = tipo_importo;
    }

    public String getFornitore() {
        return fornitore;
    }

    public void setFornitore(String fornitore) {
        this.fornitore = fornitore;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public boolean isMail_preavviso() {
        return mail_preavviso;
    }

    public boolean isMail_contratto() {
        return mail_contratto;
    }

    public Set<FileContrat> getFileContrats() {
        return fileContrats;
    }

    public void setFileContrats(Set<FileContrat> fileContrats) {
        this.fileContrats = fileContrats;
    }

    public String getLop_cliente() {
        return lop_cliente;
    }

    public void setLop_cliente(String lop_cliente) {
        this.lop_cliente = lop_cliente;
    }

    public String getTipologia_contratto() {
        return tipologia_contratto;
    }

    public void setTipologia_contratto(String tipologia_contratto) {
        this.tipologia_contratto = tipologia_contratto;
    }

    public String getStato_contratto() {
        return stato_contratto;
    }

    public void setStato_contratto(String stato_contratto) {
        this.stato_contratto = stato_contratto;
    }

    public String getData_validata() {
        return data_validata;
    }

    public void setData_validata(String data_validata) {
        this.data_validata = data_validata;
    }

    public String getRinnovo_automatico() {
        return rinnovo_automatico;
    }

    public void setRinnovo_automatico(String rinnovo_automatico) {
        this.rinnovo_automatico = rinnovo_automatico;
    }

    public String getData_disdetta() {
        return data_disdetta;
    }

    public void setData_disdetta(String data_disdetta) {
        this.data_disdetta = data_disdetta;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean getMail_preavviso() {
        return mail_preavviso;
    }

    public void setMail_preavviso(boolean mail_preavviso) {
        this.mail_preavviso = mail_preavviso;
    }

    public boolean getMail_contratto() {
        return mail_contratto;
    }

    public void setMail_contratto(boolean mail_contratto) {
        this.mail_contratto = mail_contratto;
    }

    public String getData_rinnovo() {
        return data_rinnovo;
    }

    public void setData_rinnovo(String data_rinnovo) {
        this.data_rinnovo = data_rinnovo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getPreavviso() {
        return preavviso;
    }

    public void setPreavviso(int preavviso) {
        this.preavviso = preavviso;
    }

    public String getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(String data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}

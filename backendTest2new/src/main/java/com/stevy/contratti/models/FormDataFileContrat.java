package com.stevy.contratti.models;

import org.springframework.web.multipart.MultipartFile;

public class FormDataFileContrat {

    private Long id;
    private String tipo_doc;
    private String note;
    private MultipartFile documenti;
    private Long id_contrat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public MultipartFile getDocumenti() {
        return documenti;
    }

    public void setDocumenti(MultipartFile documenti) {
        this.documenti = documenti;
    }

    public Long getId_contrat() {
        return id_contrat;
    }

    public void setId_contrat(Long id_contrat) {
        this.id_contrat = id_contrat;
    }
}

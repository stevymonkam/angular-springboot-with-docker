package com.stevy.contratti.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@ToString
public class FileContrat {
    @Id
    @GeneratedValue
    private Long id;
    private String tipo_doc;
    private String note;
    private String documenti;
    private String created_at;
    private String updated_at;



    public FileContrat() {
    }

    public FileContrat(String tipo_doc, String documenti) {
        this.tipo_doc = tipo_doc;
        this.documenti = documenti;
    }

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

    public String getDocumenti() {
        return documenti;
    }

    public void setDocumenti(String documenti) {
        this.documenti = documenti;
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

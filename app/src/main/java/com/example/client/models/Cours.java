package com.example.client.models;

public class Cours {

    private String nom = "";
    private String details = "";
    private Integer nombreLesson = 0;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getNombreLesson() {
        return nombreLesson;
    }

    public void setNombreLesson(Integer nombreLesson) {
        this.nombreLesson = nombreLesson;
    }
}

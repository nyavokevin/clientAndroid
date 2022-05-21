package com.example.client.models;

public class Cours {
    private String name;
    private String details;
    private String intervale;
    private String image;

    /**
     * Constructeur de la classe cours
     * @param name
     * @param details
     * @param intervale
     * @param image
     */
    public Cours(String name, String details, String intervale, String image) {
        this.name = name;
        this.details = details;
        this.intervale = intervale;
        this.image = image;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setDetails(String details) { this.details = details; }

    public String getDetails() { return details; }

    public void setIntervale(String intervale) { this.intervale = intervale; }

    public String getIntervale() { return intervale; }

    public void setImage(String image) { this.image = image; }

    public String getImage() { return image; }

}

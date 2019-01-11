package com.jtm.FCCweb.FCCweb.model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class FCmaker {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String phraseToTranslate;

    private String translation;
    private String meaning;

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setPhraseToTranslate(String phraseToTranslate) { this.phraseToTranslate = phraseToTranslate; }

    public String getPhraseToTranslate() { return phraseToTranslate; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FCmaker(Integer id, String phraseToTranslate) {
        this.id = id;
        this.phraseToTranslate = phraseToTranslate;
    }

    public FCmaker() {
    }
}

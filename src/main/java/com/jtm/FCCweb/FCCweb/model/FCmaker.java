package com.jtm.FCCweb.FCCweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.HashMap;
import java.util.Map;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCmaker {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phraseToTranslate;
    private String fromLanguage;
    private String destLanguage;

    private String translation;
    private String meaning;
    private String examplesURL;


}

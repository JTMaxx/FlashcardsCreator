package com.jtm.FCCweb.FCCweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCmaker {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String phraseToTranslate;
    private String selectedTransWay;

    private String translation;
    private String meaning;


}

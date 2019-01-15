package com.jtm.FCCweb.FCCweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Map;


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
//    private Map.Entry<String, String> examplesMap = new Map.Entry<String, String>() {
//        @Override
//        public String getKey() {
//            return null;
//        }
//
//        @Override
//        public String getValue() {
//            return null;
//        }
//
//        @Override
//        public String setValue(String value) {
//            return null;
//        }
//    }


}

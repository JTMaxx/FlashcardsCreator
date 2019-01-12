package com.jtm.FCCweb.FCCweb;

import java.util.Scanner;

public class CommunicationWithUser {
   GlosbeAPItranslationModel glosbeAPItranslationModel = new GlosbeAPItranslationModel();

   //todo: make this method getter instead of setter and call it anywhere!
    void setTranslationWay() {
        Scanner scanTransWay = new Scanner(System.in);
        System.out.println("polish -> english\nchoose 1\nenglish->polish\nchoose 2\nother translation\nchoose 3");
        int transWayChoice = scanTransWay.nextInt();

        if (transWayChoice == 1) {
            glosbeAPItranslationModel.setFrom("pol");
            glosbeAPItranslationModel.setDest("eng");
        }
        else if (transWayChoice == 2) {

            glosbeAPItranslationModel.setFrom("eng");
            glosbeAPItranslationModel.setDest("pol");
        }
        else if (transWayChoice == 3) {
            Scanner scanOtherTrans = new Scanner(System.in);
            System.out.println("Use shortcuts like 'pol', 'fra', 'deu'\nPut language which you want translate from");
            glosbeAPItranslationModel.setFrom(scanOtherTrans.nextLine());
            System.out.println("Put destination language");
            glosbeAPItranslationModel.setDest(scanOtherTrans.nextLine());
            //scanOtherTrans.close(); // Why does it cause also closing of scanApiParam? Because it actually close both (scanOtherTrans and scanApiParam) underlying stream 'System.in'
        }
    }

//    void userSetPhraseToTranslate() {
//        Scanner scanApiParam = new Scanner(System.in);
//        System.out.println("Put phrase to translate");
//        glosbeAPItranslationModel.setPhraseToTranslate(scanApiParam.next()); //error at reading value from scanner if other translations
//        //scanApiParam.close();
//    }

}

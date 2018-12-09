import java.util.Scanner;

public class CommunicationWithUser {
   //APIparameters apiParameters = new APIparameters();

    void setTranslationWay(APIparameters apiParams) {
        Scanner scanTransWay = new Scanner(System.in);
        System.out.println("polish -> english\nchoose 1\nenglish->polish\nchoose 2\nother translation\nchoose 3");
        int transWayChoice = scanTransWay.nextInt();

        if (transWayChoice == 1) {
            apiParams.setFrom("pol");
            apiParams.setDest("eng");
        }
        else if (transWayChoice == 2) {

            apiParams.setFrom("eng");
            apiParams.setDest("pol");
        }
        else if (transWayChoice == 3) {
            Scanner scanOtherTrans = new Scanner(System.in);
            System.out.println("Use shortcuts like 'pol', 'fra', 'deu'\nPut language which you want translate from");
            apiParams.setFrom(scanOtherTrans.nextLine());
            System.out.println("Put destination language");
            apiParams.setDest(scanOtherTrans.nextLine());
            //scanOtherTrans.close(); // Why does it cause also closing of scanApiParam? Because it actually close both (scanOtherTrans and scanApiParam) underlying stream 'System.in'
        }
    }
}

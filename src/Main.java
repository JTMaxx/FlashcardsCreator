import java.lang.String;

public class Main {


    public static void main(String[] args) {
        CommunicationWithUser communicationWithUser = new CommunicationWithUser();
        FlashcardsCreator flashcardsCreator = new FlashcardsCreator();
        communicationWithUser.setTranslationWay();
        flashcardsCreator.getDataFromJson(communicationWithUser.apiParameters, communicationWithUser);



    }
}



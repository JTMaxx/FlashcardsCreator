package com.jtm.FCCweb.FCCweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);

		CommunicationWithUser communicationWithUser = new CommunicationWithUser();
		FlashcardsCreator flashcardsCreator = new FlashcardsCreator();
		communicationWithUser.setTranslationWay();

		flashcardsCreator.printFlashcards(communicationWithUser.glosbeAPItranslationModel, communicationWithUser, flashcardsCreator.websiteProvider);
	}

}


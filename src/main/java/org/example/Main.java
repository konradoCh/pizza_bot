package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            Responder responder = new Responder(Bot.BOT_TOKEN);
            telegramBotsApi.registerBot(responder);

            MongoDB.connectToDatabase();

        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }
}
package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Responder extends TelegramLongPollingBot {

    public Responder(String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return Bot.USERNAME;
    }
}

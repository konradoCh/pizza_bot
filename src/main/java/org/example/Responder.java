package org.example;

import org.example.strategy.CallbackStrategy;
import org.example.strategy.TextStrategy;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Logger;
import static java.util.logging.Level.*;


public class Responder extends TelegramLongPollingBot {

    Logger LOGGER = Logger.getLogger(Responder.class.getName());

    public Responder(String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {

            SendMessage response = null;

            if (update.hasCallbackQuery()) {  //hasCallbackQuery - zdarzenia gdy user kliknie przycisk
                response = new CallbackStrategy().getResponse(update);
            }

            if (update.hasMessage()) {
                response = new TextStrategy().getResponse(update);
            }

            if (response == null) {
                LOGGER.log(WARNING, "Update type could not be determined", update);
                return;
            }

            sendApiMethod(response);

        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return Bot.USERNAME;
    }
}

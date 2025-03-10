package org.example.strategy;

import org.example.PizzaStore;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TextStrategy implements Strategy{

    @Override
    public SendMessage getResponse(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        SendMessage response = new SendMessage();
        String defaultResponse = "I'm sorry, but I do not understand message. Please tap /start to start over if you are lost";

        response.setChatId(chatId);
        response.setText(defaultResponse);

        String textUpdate = update.getMessage().getText().trim();

        if (textUpdate.equalsIgnoreCase("/start")) {
            response.setText("Welcome to Pizza Inc. Please select the pizza option you would like to have");

            //First create the keyboard
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(); // tworzenie przycisków

            //Then we create the buttons: row
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (String pizza: PizzaStore.PIZZA_TYPE_LIST) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(pizza);
                button.setCallbackData(pizza);
                buttonsRow.add(button);
            }

            //We add the newly created buttons row that contains the yes button to the keyboard
            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);
        }

        return response;
    }
}

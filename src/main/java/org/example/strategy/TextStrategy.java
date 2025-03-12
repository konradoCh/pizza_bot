package org.example.strategy;

import org.example.Calculator;
import org.example.CommonMessages;
import org.example.MongoDB;
import org.example.PizzaStore;
import org.example.enums.OrderState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TextStrategy implements Strategy {
    @Override
    public SendMessage getResponse(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        SendMessage response = new SendMessage();

        response.setChatId(chatId);
        response.setText(CommonMessages.COMMON_RESPONSE);

        String textUpdate = update.getMessage().getText().trim();

        if (textUpdate.equalsIgnoreCase("/start")) {
            response.setText("Welcome to Pizza Inc. Please select the pizza option you would like to have");

            //First create the keyboard
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(); // tworzenie przycisków

            //Then we create the buttons: row
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (String pizza : PizzaStore.PIZZA_TYPE_LIST) {
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

        if (MongoDB.getFieldValue(MongoDB.ORDER_STATE, chatId).equalsIgnoreCase(OrderState.ADDRESS.toString())) {
            MongoDB.updateField(MongoDB.ADDRESS, textUpdate, chatId);

            MongoDB.updateField(MongoDB.ORDER_STATE, OrderState.PAYMENT.toString(), chatId);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The final total price to be paid is goint to be:\n\n $").append(Calculator.getFinalPrice(chatId)).append("\n\nto get back to main menu type /start");
            response.setText(stringBuilder.toString());

        }

        return response;
    }

}
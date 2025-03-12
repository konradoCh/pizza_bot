package org.example.strategy;

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
import java.util.Map;

public class CallbackStrategy implements Strategy {

    @Override
    public SendMessage getResponse(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(CommonMessages.COMMON_RESPONSE);

        String callBackResponse = update.getCallbackQuery().getData();

        if (PizzaStore.PIZZA_TYPE_LIST.contains(callBackResponse)) {
            MongoDB.updateField(MongoDB.PIZZA_TYPE, callBackResponse, chatId);

            //Setting message response
            response.setText("Select the pizza size you'd like to have");

            //First create the keyboard
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(); // tworzenie przycisków

            //Then we create the buttons: row
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (Map.Entry<String, Double> set : PizzaStore.PIZZA_SIZE_MAP.entrySet()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(set.getKey()).append(": $").append(set.getValue());
                button.setText(stringBuilder.toString());
                button.setCallbackData(set.getKey().toString());
                buttonsRow.add(button);
            }


            //We add the newly created buttons row that contains the yes button to the keyboard
            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);

        }

        if (PizzaStore.PIZZA_SIZE_MAP.containsKey(callBackResponse)) {
            MongoDB.updateField(MongoDB.PIZZA_SIZE, callBackResponse, chatId);

            response.setText("Please select the drink you'd like to have");

            //First create the keyboard
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(); // tworzenie przycisków

            //Then we create the buttons: row
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (Map.Entry<String, Double> set : PizzaStore.DRINKS_MAP.entrySet()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(set.getKey()).append(": $").append(set.getValue());
                button.setText(stringBuilder.toString());
                button.setCallbackData(set.getKey().toString());
                buttonsRow.add(button);
            }

            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);
        }

        if (PizzaStore.DRINKS_MAP.containsKey(callBackResponse)) {
            MongoDB.updateField(MongoDB.DRINK, callBackResponse, chatId);
            MongoDB.updateField(MongoDB.ORDER_STATE, OrderState.ADDRESS.toString(), chatId);

            response.setText("Please type address to which the delivery og the pizza will go to");
        }

        return response;
    }
}

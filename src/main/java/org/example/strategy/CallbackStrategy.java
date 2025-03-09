package org.example.strategy;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackStrategy implements Strategy{

    @Override
    public SendMessage getResponse(Update update) {
        return null;
    }
}

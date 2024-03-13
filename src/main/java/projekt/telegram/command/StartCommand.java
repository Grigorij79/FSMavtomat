package com.javacore5.feate.currency.command;

//import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import com.javacore5.feate.currency.Currency;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String text = "Курc валют";
        SendMessage message = new SendMessage();
        message.setText( new String(text.getBytes(), StandardCharsets.UTF_8));
         message.setChatId(chat.getId().toString());

        List <InlineKeyboardButton> buttons = Stream.of(Currency.USD, Currency.EUR)
                .map(Enum::name)
                .map(it -> InlineKeyboardButton.builder().text(it).callbackData(it).build())
                .collect(Collectors.toList());

/*
        InlineKeyboardButton usdButton = InlineKeyboardButton
                .builder()
                .text("USD")
                .callbackData("Usd")
                .build();

 */
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singleton(buttons))
                .build();
        message.setReplyMarkup(keyboard);


        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Start pressed!");
    }
}

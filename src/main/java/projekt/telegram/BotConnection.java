package com.javacore5.feate.currency.telegram;

import com.javacore5.feate.currency.Currency;
import com.javacore5.feate.currency.CurrencyServise;
import com.javacore5.feate.currency.PrivatBankCurrencyServise;
import com.javacore5.feate.currency.command.StartCommand;
import com.javacore5.feate.currency.ui.PrettyPrintCurrencyServise;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class BotConnection extends TelegramLongPollingCommandBot {
    private CurrencyServise currencyServise;
    private PrettyPrintCurrencyServise prettyPrintCurrencyServise;


    public BotConnection(){
        this.currencyServise = new PrivatBankCurrencyServise();
        this.prettyPrintCurrencyServise = new PrettyPrintCurrencyServise();
        register(new StartCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update)  {
        if (update.hasCallbackQuery()){
            String callBackQuery = update.getCallbackQuery().getData();
            Currency currency = Currency.valueOf(callBackQuery);

            System.out.println("callBackQuery = " + callBackQuery);
            System.out.println("currency = " + currency);
            double curencyRate = 0;
            try {
                curencyRate = currencyServise.getRate(currency);
                System.out.println("curencyRate = " + curencyRate);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String pretyText = prettyPrintCurrencyServise.convert(curencyRate, currency);

            System.out.println("pretyText = " + pretyText);
            SendMessage responseMasage = new SendMessage();
                responseMasage.setText(new String(pretyText.getBytes(), StandardCharsets.UTF_8));

                long chatId = update.getCallbackQuery().getMessage().getChatId();
                responseMasage.setChatId(Long.toString(chatId));
            try {
                execute(responseMasage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }


            System.out.println("callBackQuery = " + callBackQuery);
        }

        //System.out.println("Non-command here!");
    }

    @Override
    public String getBotUsername() {
        return BotConstans.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstans.BOT_TOKEN;
    }



}

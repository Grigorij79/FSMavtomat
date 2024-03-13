package projekt;
import projekt.bank.Currency;
import projekt.bank.PrettyPrintCurrencyServise;
import projekt.bank.PrivatBankCurrencyServise;
import projekt.telegram.BotInitialization;

import java.io.IOException;


public class TelegramBotApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        CurrencyServise currencyServise = new PrivatBankCurrencyServise();
        Currency currency = Currency.EUR;
        double rate = currencyServise.getRate(currency);

        String printText = new PrettyPrintCurrencyServise().convert(rate, currency);
        System.out.println("printText = " + printText);

        BotInitialization botService = new BotInitialization();
    }
}

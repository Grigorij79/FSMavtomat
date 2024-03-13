package projekt;

import projekt.bank.Currency;

import java.io.IOException;

public interface CurrencyServise {
    double getRate (Currency currency) throws IOException, InterruptedException;
}

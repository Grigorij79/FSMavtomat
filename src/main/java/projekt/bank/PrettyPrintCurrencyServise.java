package projekt.bank;

public class PrettyPrintCurrencyServise {
    public String convert (double rate, Currency currency){


        String template = "Курc ${currency} => UAH = ${rate}";

        float roundRate = Math.round(rate * 100d)/ 100f;

        return template.replace("${currency}",currency.name())
                .replace("${rate}", roundRate + " ");
    }
}

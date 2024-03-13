package projekt.bank;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import projekt.CurrencyServise;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrivatBankCurrencyServise implements CurrencyServise {
    @Override
    public double getRate(Currency currency) throws IOException, InterruptedException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HttpClient client = HttpClient.newHttpClient();
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        //Get JSON
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Convert json => Java Object

        CurrencyBot[] todosArray = gson.fromJson(response.body(), (Type) CurrencyBot[].class);
        List<CurrencyBot> curencyList = new ArrayList<>(Arrays.asList(todosArray));

        //Find UAH/USD/EUR

        return curencyList.stream()
                .filter(x -> x.getCcy() == currency)
                .map(x-> x.getBuy())
                .findFirst()
                .orElseThrow();
    }
}
class CurrencyBot {
    private Currency ccy;
    private Currency base_ccy;
    private float buy;
    private float sale;

    public Currency getCcy() {
        return ccy;
    }

    public void setCcy(Currency ccy) {
        this.ccy = ccy;
    }

    public Currency getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(Currency base_ccy) {
        this.base_ccy = base_ccy;
    }

    public float getBuy() {
        return buy;
    }

    public void setBuy(float buy) {
        this.buy = buy;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }


}


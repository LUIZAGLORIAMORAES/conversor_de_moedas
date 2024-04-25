import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateService {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/b1b9117c810ecf923a6f253e/latest/";
    private final Gson gson;

    public ExchangeRateService() {
        gson = new Gson();
    }

    public double getExchangeRate(String currencyFrom, String currencyTo) throws IOException {
        URL url = new URL(API_URL + currencyFrom);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JsonObject responseJson = gson.fromJson(reader, JsonObject.class);
            reader.close();

            JsonObject rates = responseJson.getAsJsonObject("conversion_rates");
            return rates.get(currencyTo).getAsDouble();
        } else {
            throw new IOException("Failed to fetch exchange rates. Response code: " + responseCode);
        }
    }
}

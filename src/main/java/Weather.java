import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    private final String thunderstorm = "⛈";
    private final String rain = "🌧";
    private final String snow = "🌨";
    private final String fog = "\uD83C\uDF2B";
    private final String clearSky = "☀";
    private final String clouds = "☁️";
    private final String URL_REQUEST = "https://api.openweathermap.org/data/2.5/weather?units=metric&lang=ru&appid=";
    private final String TOKEN = "7edf08a03f8984258d70b2610de1c228";
    private final Model model = new Model();

    public String getWeather(String message) throws IOException {
        String result = "";
        URL urlRequest = new URL(createURLRequest() + message);
        Scanner in = new Scanner((InputStream) urlRequest.getContent());
        while (in.hasNext()) {
            result += in.nextLine();
        }
        parseJSON(result);
        return weatherString();
    }

    private String createURLRequest() {
        return URL_REQUEST + TOKEN + "&q=";
    }

    private void parseJSON(String input) {
        JSONObject jsonObject = new JSONObject(input);
        model.setName(jsonObject.getString("name"));

        JSONObject jsonMain = jsonObject.getJSONObject("main");
        model.setHumidity(jsonMain.getInt("humidity"));
        model.setTemperature(jsonMain.getInt("temp"));

        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        JSONObject obj = jsonArray.getJSONObject(0);
        model.setWeatherID(obj.getInt("id"));
        model.setMain(obj.getString("description"));
    }

    private String weatherString() {
        return model.getName() + "\n" +
                getWeatherEmoji() + "\n" +
                model.getMain() + "\n" +
                "Температура " + model.getTemperature() + "C\n" +
                "Влажность воздуха " + model.getHumidity() + "%\n";
    }

    public String getWeatherEmoji() {
        if (model.getId() / 100 == 2) {
            return thunderstorm;
        } else if (model.getId() / 100 == 6 || model.getId() == 511) {
            return snow;
        } else if (model.getId() / 100 == 7) {
            return fog;
        } else if (model.getId() > 800) {
            return clouds;
        } else if (model.getId() == 800) {
            return clearSky;
        } else return rain;
    }
}

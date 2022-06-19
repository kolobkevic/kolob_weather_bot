import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Weather {
    private final String URL_REQUEST = "https://api.openweathermap.org/data/2.5/weather?appid=7edf08a03f8984258d70b2610de1c228&units=metric&lang=ru&q=";
    private final Model model = new Model();

    public String getWeather(String message) throws IOException {
        String result = "";
        URL urlRequest = new URL(URL_REQUEST + message);
        Scanner in = new Scanner((InputStream) urlRequest.getContent());
        while (in.hasNext()) {
            result += in.nextLine();
        }
        parseJSON(result);
        return weatherString();
    }

    private void parseJSON(String input) {
        JSONObject jsonObject = new JSONObject(input);
        model.setName(jsonObject.getString("name"));

        JSONObject jsonMain = jsonObject.getJSONObject("main");
        model.setHumidity(jsonMain.getDouble("humidity"));
        model.setTemperature(jsonMain.getDouble("temp"));

        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        JSONObject obj = jsonArray.getJSONObject(0);
        model.setIcon(obj.getString("icon"));
        model.setMain(obj.getString("description"));
    }

    private String weatherString() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return model.getName() + "\n" +
                formatter.format(date) + "\n" +
                model.getMain() + "\n" +
                "Температура " + model.getTemperature() + "C\n" +
                "Влажность воздуха " + model.getHumidity() + "%\n";
    }

    public String getPictureURL() {
        return "http://openweathermap.org/img/wn/" + model.getIcon() + ".png";
    }
}

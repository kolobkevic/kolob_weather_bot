import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "kolob_weather_bot";
    }

    @Override
    public String getBotToken() {
        return "5585456003:AAGzQ0ZBKe2LGAOOitGXdfIY28vEHuAy8Cs";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            SendPhoto photo = null;
            Weather weather = new Weather();
            try {
                message.setText(weather.getWeather(update.getMessage().getText()));
                photo = sendImageFromUrl(weather.getPictureURL(), update.getMessage().getChatId().toString());
            } catch (IOException e) {
                message.setText("Город не найден");
            }

            try {
                execute(message);
                execute(photo);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public SendPhoto sendImageFromUrl(String url, String chatId) {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(new InputFile(url));
        return sendPhotoRequest;
    }

}
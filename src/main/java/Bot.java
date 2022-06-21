import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

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
            try {
                message.setText(new Weather().getWeather(update.getMessage().getText()));
            } catch (IOException e) {
                message.setText("Город не найден");
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
package com.telegrambots.firstcook;

import com.telegrambots.firstcook.model.tUser;
import com.telegrambots.firstcook.service.UsersProfileMongoRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class myBot extends TelegramLongPollingBot {
    @Value("${telegramBot.userName}")
    private String userName;
    @Value("${telegramBot.botToken}")
    private String botToken;


    private UsersProfileMongoRepositoryImpl systemBot;
    private Random random;

    @Autowired
    public myBot(UsersProfileMongoRepositoryImpl systemBot) {
        this.systemBot = systemBot;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String chat_id = String.valueOf(update.getMessage().getChatId());
            String textMessage = update.getMessage().getText().toLowerCase();
            SendMessage message = new SendMessage();
            tUser tUser = new tUser();
            message.setChatId(chat_id);
            int count = 0;
            int random1 = random.nextInt(30);

            if (!textMessage.isEmpty()) {
                count++;
                if (count == random1) {
                    try {
                        message.setReplyToMessageId(update.getMessage().getMessageId());
                        message.setText("А ты походу шаришь");
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (textMessage.startsWith("/adduser")) {
                try {
                    tUser.setUsername(textMessage.substring(8).trim());
                    tUser.setChat_id(chat_id);
                    tUser.setId(update.getMessage().getMessageId().toString());
                    systemBot.addUserForDB(tUser);
                    message.setText("Записала, шеф.");
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if ((textMessage.startsWith("/all") || (textMessage.startsWith("@all")))) {
                try {
                    message.setText("Ага, вот эти ребята: " + systemBot.getAllUserForDB(update.getMessage().getChatId().toString()));
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (textMessage.contains("фронт") || textMessage.contains("front") || textMessage.contains("frontend") || textMessage.contains("front-end")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Frontend для пидоров");
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (textMessage.contains("бэк") || textMessage.contains("backend") || textMessage.contains("бекенд")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Бэкенд для солидных господ, мое увожение ");
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

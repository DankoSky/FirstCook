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
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
            int count = update.getMessage().getMessageId();

            if (textMessage.startsWith("/adduser")) {
                tUser.setUsername(textMessage.substring(8).trim());
                tUser.setChat_id(chat_id);
                tUser.setId(update.getMessage().getMessageId().toString());
                systemBot.addUserForDB(tUser);
                message.setText("Записала, шеф.");
                execute(message);
            } else if ((textMessage.startsWith("/all") || (textMessage.startsWith("@all")))) {
                message.setText("Ага, вот эти ребята: " + systemBot.getAllUserForDB(update.getMessage().getChatId().toString()));
                execute(message);
            }

            if (textMessage.contains("фронт") || textMessage.contains("front") || textMessage.contains("frontend") || textMessage.contains("front-end")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Frontend для пидоров");
                    TimeUnit.SECONDS.sleep(5);
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (textMessage.contains("бэк") || textMessage.contains("backend") || textMessage.contains("бекенд")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Бэкенд для солидных господ, мое увожение ");
                    TimeUnit.SECONDS.sleep(5);
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (count % 100 == 0) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("А ты походу шаришь");
                    TimeUnit.SECONDS.sleep(5);
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (count % 5 == 0) {
                try {
                    message.setText("test");
                    execute(message);
                    InputFile inputFile = new InputFile("C:\\Users\\Bublic\\IdeaProjects\\FirstCook\\src\\main\\resources\\templates\\Foto.png");
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setCaption("My Caption!");
                    sendPhoto.setPhoto(inputFile);
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
/*public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String chat_id = String.valueOf(update.getMessage().getChatId());
            String textMessage = update.getMessage().getText().toLowerCase();
            SendMessage message = new SendMessage();
            tUser tUser = new tUser();
            message.setChatId(chat_id);

            if (textMessage.startsWith("/adduser")) {
                tUser.setUsername(textMessage.substring(8).trim());
                tUser.setChat_id(chat_id);
                tUser.setId(update.getMessage().getMessageId().toString());
                systemBot.addUserForDB(tUser);
                message.setText("Записала, шеф.");
                execute(message);
            } else if ((textMessage.startsWith("/all") || (textMessage.startsWith("@all")))) {
                message.setText("Ага, вот эти ребята: " + systemBot.getAllUserForDB(update.getMessage().getChatId().toString()));
                execute(message);
            } else if (textMessage.contains("фронт") || textMessage.contains("front") || textMessage.contains("frontend") || textMessage.contains("front-end")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Frontend для пидоров");
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (textMessage.contains("бэк") || textMessage.contains("backend") || textMessage.contains("бекенд")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Бэкенд для солидных господ, мое увожение");
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

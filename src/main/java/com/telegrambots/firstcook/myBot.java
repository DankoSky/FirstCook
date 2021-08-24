package com.telegrambots.firstcook;

import ch.qos.logback.core.pattern.parser.Parser;
import com.telegrambots.firstcook.model.tUser;
import com.telegrambots.firstcook.service.UsersProfileMongoRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.util.Comparator;
import java.util.List;
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
            if (count % 150 == 0) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("А ты походу шаришь");
                    TimeUnit.SECONDS.sleep(5);
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (count % 100 == 0) {
                Random random = new Random();
                int i = random.nextInt(3);
                String url1 = "https://krasivosti.pro/uploads/posts/2021-06/1623640057_48-krasivosti_pro-p-amerikanskii-barsuk-zhivotnie-krasivo-foto-49.jpg";
                String url2 = "https://img3.goodfon.ru/wallpaper/nbig/0/3c/minions-bob-look-happy.jpg";
                String url3 = "https://cs8.pikabu.ru/post_img/2016/04/03/11/145970901515739079.png";

                if (i == 0) {
                    sendImageFromUrl(url1, chat_id);
                }
                if (i == 1) {
                    sendImageFromUrl(url2, chat_id);
                }
                if (i == 2) {
                    sendImageFromUrl(url3, chat_id);
                }
            }
        }
    }

    public void sendImageFromUrl(String url, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo url as a simple photo
        sendPhotoRequest.setPhoto(new InputFile(url));
        try {
            // Execute the method
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendImageFromFileId(String fileId, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo url as a simple photo
        sendPhotoRequest.setPhoto(new InputFile(fileId));
        try {
            // Execute the method
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendImageUploadingAFile(String filePath, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo file as a new photo (You can also use InputStream with a constructor overload)
        sendPhotoRequest.setPhoto(new InputFile(new File(filePath)));
        try {
            // Execute the method
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


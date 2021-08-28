package com.telegrambots.firstcook;

import com.telegrambots.firstcook.model.tUser;

import com.telegrambots.firstcook.service.tUserServiceImpl;
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
import java.util.ArrayList;
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

    private tUserServiceImpl systemBot;

    @Autowired
    public myBot(tUserServiceImpl systemBot) {   
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

    private List<String> picture = new ArrayList<String>() {{
        add("https://img3.goodfon.ru/wallpaper/nbig/0/3c/minions-bob-look-happy.jpg");
        add("https://cs8.pikabu.ru/post_img/2016/04/03/11/145970901515739079.png");
        add("https://krasivosti.pro/uploads/posts/2021-06/1623640057_48-krasivosti_pro-p-amerikanskii-barsuk-zhivotnie-krasivo-foto-49.jpg");
    }};

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {


        if (update.getMessage() != null && update.getMessage().hasText()) {

            String chat_id = update.getMessage().getChatId().toString();
            String textMessage = update.getMessage().getText().toLowerCase();
            SendMessage message = new SendMessage();

            message.setChatId(chat_id);
            int count = update.getMessage().getMessageId();  //  id сообщения, чтобы выбрасывать в определенный момент событие.
            Random random = new Random();

            if (textMessage.startsWith("/adduser")) {
                tUser user = tUser.builder()
                        .username(textMessage.substring(8).trim())
                        .chat_id(chat_id)
                        .id(update.getMessage().getMessageId())
                        .build();
                systemBot.addUserForDB(user);
                message.setText("Записала, шеф.");
                execute(message);
            }

            if (textMessage.startsWith("/adr") & textMessage.endsWith("dr")) {
                systemBot.setBirthday(textMessage.substring(4, textMessage.length() - 11).trim(), textMessage.substring((textMessage.length() - textMessage.substring(11).trim().length()), textMessage.length() - 3));
                message.setText("Записала др, шеф.");
                execute(message);
            }


            if ((textMessage.contains("/all") || (textMessage.contains("@all")))) {
                message.setText("Ага, вот эти ребята: " + systemBot.getAllUserForDB(chat_id));
                execute(message);
            }

            if ((textMessage.startsWith("/dr") || (textMessage.startsWith("@dr")))) {
                if (textMessage.length() == 3) {
                    message.setText(systemBot.getAllUsersAndBirthday(chat_id));
                } else {
                    message.setText(systemBot.getUserByUsername(textMessage.substring(3).trim()).username
                            + " : " + systemBot.getUserByUsername(textMessage.substring(3).trim()).birthday);
                }
                execute(message);
            }

            if (textMessage.contains("фронт") || textMessage.contains("front") || textMessage.contains("frontend") || textMessage.contains("front-end")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Frontend для пидоров");
                    int k = random.nextInt(5);
                    TimeUnit.SECONDS.sleep(k);
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (textMessage.contains("бэк") || textMessage.contains("backend") || textMessage.contains("бекенд")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Бэкенд для солидных господ, мое увожение ");
                    int j = random.nextInt(5);
                    TimeUnit.SECONDS.sleep(j);
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
                int i = random.nextInt(3);
                sendImageFromUrl(picture, i, chat_id);
            }
        }
    }


    public void sendImageFromUrl(List<String> picture, Integer random, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo url as a simple photo
        sendPhotoRequest.setPhoto(new InputFile(picture.get(random)));
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


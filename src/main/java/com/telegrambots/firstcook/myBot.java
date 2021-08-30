package com.telegrambots.firstcook;

import com.telegrambots.firstcook.model.tUser;

import com.telegrambots.firstcook.service.tUserServiceImpl;
import lombok.*;
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

@EqualsAndHashCode(callSuper = true)
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
        add("https://sun9-70.userapi.com/impf/c629226/v629226240/2c1df/p2f4otGoXE4.jpg?size=800x450&quality=96&keep_aspect_ratio=1&background=000000&sign=dbecfb7e67db5421e86e038afcf9d88b&type=video_thumb");
        add("https://cs8.pikabu.ru/post_img/big/2016/12/27/4/148281608619023689.png");
    }};

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String chat_id = update.getMessage().getChatId().toString();
        String textMessage = update.getMessage().getText().toLowerCase();
        SendMessage message = new SendMessage();
        tUser user = new tUser();
        message.setChatId(chat_id);
        int count = update.getMessage().getMessageId();  //  id сообщения, чтобы выбрасывать в определенный момент событие.
        Random random = new Random();

        if (update.getMessage() != null && update.getMessage().hasText()) {
            if (textMessage.contains("фронт") || textMessage.contains("front") || textMessage.contains("frontend") || textMessage.contains("front-end")) {
                message.setText("Frontend для пидоров");
                message.setReplyToMessageId(update.getMessage().getMessageId());
                TimeUnit.SECONDS.sleep(3);
                execute(message);
            }
            if (textMessage.contains("бэк") || textMessage.contains("backend") || textMessage.contains("бекенд")) {
                message.setText("Бэкенд для солидных господ, мое увожение ");
                message.setReplyToMessageId(update.getMessage().getMessageId());
                TimeUnit.SECONDS.sleep(3);
                execute(message);
            }
            if (count % 150 == 0) {
                message.setText("А ты походу шаришь");
                message.setReplyToMessageId(update.getMessage().getMessageId());
                TimeUnit.SECONDS.sleep(3);
                execute(message);
            }
            if (count % 70 == 0) {
                int i = random.nextInt(picture.size());
                sendImageFromUrl(picture, i, chat_id);
            }
            if ((textMessage.startsWith("/all") || (textMessage.startsWith("@all")))) {
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
        }
        user = systemBot.getUserByUsername(update.getMessage().getFrom().getUserName());
        if (update.getMessage() != null && update.getMessage().hasText() && textMessage.startsWith("/") && user.isAdmin()) {
            if (textMessage.startsWith("/adu")) {
                user.setUsername(textMessage.substring(4).trim());
                user.setChat_id(chat_id);
                systemBot.addUserForDB(user);
                message.setText("Записала, шеф");
                execute(message);
            } else if (textMessage.startsWith("/adr")) {
                String username = textMessage.substring(4, textMessage.length() - 10).trim();
                String date = textMessage.substring((textMessage.length() - (6 + username.length())));
                Integer cour = date.length();
                if (date.length() != 10) {
                    message.setText("Неправильный формат даты");
                } else if (systemBot.getUserByUsername(username).username == null) {
                    message.setText("Такого юзера еще нет в БД");
                } else {
                    systemBot.setBirthday(username, date);
                    message.setText("Записала др, шеф");
                }
                execute(message);
            } else if (textMessage.startsWith("/del")) {
                message.setText(systemBot.deleteByUserName(textMessage.substring(4).trim()));
                execute(message);
            } else if (textMessage.startsWith("/set")) {
                systemBot.setAdmin(textMessage.substring(4).trim());
                message.setText("назначила, шеф");
                execute(message);
            } else {
                sendImageFromUrl(picture, 4, chat_id);
            }
        }
    }


    public void sendImageFromUrl(List<String> picture, Integer random, String chatId) {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(new InputFile(picture.get(random)));
        try {
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


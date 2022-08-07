package com.telegrambots.firstcook;

import com.telegrambots.firstcook.service.UserServiceImpl;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class myBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";
    @Value("${telegramBot.userName}")
    private String userName;
    @Value("${telegramBot.botToken}")
    private String botToken;

    private UserServiceImpl systemBot;


    @Autowired
    public myBot(UserServiceImpl systemBot) {
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


    @Scheduled(cron = "0 0 10 * * *")
    public void sendEvent() throws TelegramApiException {
        String birthday = systemBot.checkBirthday(LocalDate.now());
        SendMessage message = new SendMessage();
        message.setText(birthday);
        message.setParseMode(ParseMode.MARKDOWN);
        message.setChatId("-1001296210331");
        if (!birthday.equals("nope")) {
            execute(message);
        }
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String chat_id = update.getMessage().getChatId().toString();
        String textMessage = update.getMessage().getText().toLowerCase();

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);

        if (update.getMessage() != null && update.getMessage().hasText()) {

            if (textMessage.startsWith("/all")) {
                message.setText("Ага, вот эти ребята: " + systemBot.getAllUserForDB(chat_id));
                execute(message);
            }

            if (textMessage.startsWith("/dr")) {
                message.setText(systemBot.getAllUsersAndBirthday(chat_id));
                execute(message);
            }
        }
    }
}
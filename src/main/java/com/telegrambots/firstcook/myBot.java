package com.telegrambots.firstcook;

import com.telegrambots.firstcook.service.SystemBotImpl;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@Data
public class myBot extends TelegramLongPollingBot {
    @Value("${telegramBot.userName}")
    private String userName;
    @Value("${telegramBot.botToken}")
    private String botToken;


    private final SystemBotImpl systemBot;

    @Autowired
    public myBot(SystemBotImpl systemBot) {
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
            message.setChatId(chat_id);

            if (textMessage.startsWith("/adduser") & (update.getMessage().getFrom().getUserName().equals("Dankosky"))) {
                systemBot.addUserForDB(textMessage.substring(8).trim());
                message.setText("Записала, шеф.");
                execute(message);

            } else if ((textMessage.startsWith("/all") || (textMessage.startsWith("@all")))
                    && ((-1001296210331L == update.getMessage().getChatId()))) {
                message.setText("Ага, вот эти ребята:" + systemBot.getAllUserForDB());
                execute(message);
            } else if (textMessage.contains("фронт")
                    || textMessage.contains("frontend")
                    || textMessage.contains("front-end")) {
                try {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("Frontend для пидоров");
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
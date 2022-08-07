package com.telegrambots.firstcook;

import com.telegrambots.firstcook.command.CommandContainer;
import com.telegrambots.firstcook.model.Role;
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

    private CommandContainer commandContainer;


    @Autowired
    public myBot(UserServiceImpl systemBot, CommandContainer commandContainer) {
        this.systemBot = systemBot;
        this.commandContainer = commandContainer;
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
    //@Scheduled(fixedDelay = 1000L)
    public void sendEvent() {
        String text = "test";

        SendMessage message = new SendMessage();
        message.setText(text);
        message.setParseMode(ParseMode.MARKDOWN);
        message.setChatId("-1001296210331");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String chat_id = update.getMessage().getChatId().toString();

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);

        String textMessage = update.getMessage().getText().toLowerCase();

        AdminCommand(update, textMessage, message);

//        if (update.hasMessage() && update.getMessage().hasText()) {
//            if (textMessage.startsWith(COMMAND_PREFIX)) {
//                String commandIdentifier = textMessage.split(" ")[0].toLowerCase();
//                execute(commandContainer.retrieveCommand(commandIdentifier).execute(update));
//            } else {
//                execute(commandContainer.retrieveCommand(null).execute(update));
//            }
//        }


        if (update.getMessage() != null && update.getMessage().hasText()) {

            if (textMessage.startsWith("/all")) {
                execute(systemBot.getAllUserForDB(update));
            }

            if (textMessage.startsWith("/dr")) {
                if (textMessage.length() == 3) {
                    message.setText(systemBot.getAllUsersAndBirthday(chat_id));
                } else {
                    message.setText(systemBot.getUserByUsername(textMessage.substring(3).trim()).username
                            + " : " + systemBot.getUserByUsername(textMessage.substring(3).trim()).birthday);
                }
                execute(message);
            }
        }
    }


    private void AdminCommand(Update update, String textMessage, SendMessage message) throws TelegramApiException {
        if (textMessage.startsWith("/") && isAdmin(update)) {
            if (textMessage.startsWith("/adu")) {
                execute(systemBot.addUserForDB(update));
            } else if (textMessage.startsWith("/adr")) {
                message.setText(systemBot.setBirthday(textMessage));
            } else if (textMessage.startsWith("/del")) {
                message.setText(systemBot.deleteByUserName(textMessage.substring(4).trim()));
            } else if (textMessage.startsWith("/set")) {
                systemBot.setAdmin(textMessage.substring(4).trim());
                message.setText("Назначила, шеф");
            }
            execute(message);
        }
    }


    private boolean isAdmin(Update update) {
        String userName = "@" + update.getMessage().getFrom().getUserName().toLowerCase();
        return systemBot.getUserByUsername(userName).getIsAdmin() == Role.ADMIN;
    }
}


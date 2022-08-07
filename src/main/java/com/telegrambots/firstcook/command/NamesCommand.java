package com.telegrambots.firstcook.command;

import com.telegrambots.firstcook.service.UserServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NamesCommand implements Command {
    UserServiceImpl userService;

    public NamesCommand(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        userService.getAllUserForDB(update);
    }
}

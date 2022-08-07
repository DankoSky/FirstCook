package com.telegrambots.firstcook.command;

import com.google.common.collect.ImmutableMap;
import com.telegrambots.firstcook.service.UserServiceImpl;
import org.springframework.stereotype.Service;

import static com.telegrambots.firstcook.command.CommandName.GETALLUSER;

@Service
public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;

    public CommandContainer(UserServiceImpl userService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(GETALLUSER.getCommandName(), new NamesCommand(userService))
                .build();


    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, null);
    }
}


package com.telegrambots.firstcook.command;

public enum CommandName {

    SETROLE("/set"),
    ADDUSER("/adu"),
    ADDUSERBIRTHDAY("/adr"),
    DELETEUSER("/del"),
    GETBIRTHDAY("/dr"),
    GETALLUSER("/all");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}

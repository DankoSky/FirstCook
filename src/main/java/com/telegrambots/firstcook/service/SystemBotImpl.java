package com.telegrambots.firstcook.service;


import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemBotImpl {


    Map<Long, Object> InMemoryUsers = new HashMap<Long, Object>() {{
        put(1L, "@Dankosky");
        put(2L, "@Amitistov");
        put(3L, "@pizzareanimator");
        put(4L, "@mr_folko");
        put(5L, "@gon4iy_kot");
        put(6L, "@mot_ley");
        put(7L, "Женя");
    }};
    Long i = 1L;


    public void addUserForDB(String s) {
        InMemoryUsers.put(i, s);
        i++;
    }


    public String getAllUserForDB() {
        return InMemoryUsers.values().toString();
    }



}

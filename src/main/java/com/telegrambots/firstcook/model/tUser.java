package com.telegrambots.firstcook.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;



@Getter
@Setter
@FieldDefaults(level = AccessLevel.PUBLIC)
@Document(collection = "myFirstDatabase")
@NoArgsConstructor
@AllArgsConstructor
public class tUser implements Serializable {

    @Id
    String id;
    String username;
    String chat_id;

    public tUser(String username, String chat_id) {
        this.username = username;
        this.chat_id = chat_id;
    }
}

package com.telegrambots.firstcook.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;



@Getter
@Setter
@FieldDefaults(level = AccessLevel.PUBLIC)
@Entity
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

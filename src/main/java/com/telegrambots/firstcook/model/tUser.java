package com.telegrambots.firstcook.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.io.Serializable;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PUBLIC)
@Entity
@Table(name = "users_chat")
//@Document(collection = "myFirstDatabase")
@NoArgsConstructor
@AllArgsConstructor
public class tUser //implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String username;
    String chat_id;

    public tUser(String username, String chat_id) {
        this.username = username;
        this.chat_id = chat_id;
    }
}

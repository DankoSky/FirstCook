package com.telegrambots.firstcook.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@FieldDefaults(level = AccessLevel.PUBLIC)
@Entity
@Table(name = "sweet_kitty")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class tUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String username;
    String chat_id;
    String birthday;
    @Enumerated
    Role isAdmin;


}
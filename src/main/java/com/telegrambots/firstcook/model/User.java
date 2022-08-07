package com.telegrambots.firstcook.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PUBLIC)
@Entity
@Table(name = "sweet_kitty")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String username;
    String chat_id;
    LocalDate birthday;
    @Enumerated
    Role isAdmin;
}
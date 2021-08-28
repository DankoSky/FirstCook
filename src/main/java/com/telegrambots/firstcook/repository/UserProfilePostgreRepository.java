package com.telegrambots.firstcook.repository;

import com.telegrambots.firstcook.model.tUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfilePostgreRepository extends JpaRepository<tUser, String> {


}

package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.User;
import com.intern.backendettaba.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("emailService")
public class EmailService {

    @Autowired
    private UserRepository userRepository;

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUsersByEmail(email);
    }



}
package com.example.contro_and_graduation_work.MailSendler;

import com.example.contro_and_graduation_work.model.Film;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface MailSendler {
    void sendEmailWithAttachment(List<Film> films, MailDto mailDto) throws IOException, MessagingException;
}

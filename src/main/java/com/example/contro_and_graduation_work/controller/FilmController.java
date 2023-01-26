package com.example.contro_and_graduation_work.controller;

import com.example.contro_and_graduation_work.MailSendler.MailDto;
import com.example.contro_and_graduation_work.MailSendler.MailSendler;
import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.model.FilmParametersDto;
import com.example.contro_and_graduation_work.service.FilmService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilmController {

    private final MailSendler mailSendler;
    private final FilmService filmService;

    @GetMapping("/send")
    public String sendEmail(Model model, @ModelAttribute FilmParametersDto filmParametersDto, BindingResult bindingResult,
                            @ModelAttribute MailDto mailDto, BindingResult bindingResult2,
                            @RequestParam(required = false, defaultValue = "0") int page,
                            @RequestParam(required = false, defaultValue = "10") int size) throws MessagingException, IOException {
        if (filmParametersDto != null) {
            List<Film> films = filmService.findFromDatabase(filmParametersDto, PageRequest.of(page, size));
            mailSendler.sendEmailWithAttachment(films, mailDto);
        }
        return "the search results have been sent to your email";
    }

    @GetMapping("/search")
    public List<Film> search(Model model, @ModelAttribute FilmParametersDto filmParametersDto, BindingResult bindingResult) {

        List<Film> films = filmService.findAll(filmParametersDto);
        /*model.addAttribute("films", films);*/
        return films;
    }
}
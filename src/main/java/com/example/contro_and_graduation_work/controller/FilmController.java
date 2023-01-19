package com.example.contro_and_graduation_work.controller;

import com.example.contro_and_graduation_work.MailSendler.MailDto;
import com.example.contro_and_graduation_work.MailSendler.MyMailSendler;
import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.model.FilmParametersDto;
import com.example.contro_and_graduation_work.service.FilmService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;

//@RestController
@Controller
@RequiredArgsConstructor
public class FilmController {

    private final MyMailSendler myMailSendler;
    private final FilmService filmService;

    @GetMapping({"/"})
    public String film(Model model, FilmParametersDto filmParametersDto) {
        if (filmParametersDto != null) {
            List<Film> films = filmService.findAll(filmParametersDto);
            model.addAttribute("films", films);
        }
        return "index";
    }


    @GetMapping("/send")
    public String sendEmail(Model model, @ModelAttribute FilmParametersDto filmParametersDto, BindingResult bindingResult,
                            @ModelAttribute MailDto mailDto, BindingResult bindingResult2) throws MessagingException, IOException {
        if (filmParametersDto != null) {
            List<Film> films = filmService.findFromDatabase(filmParametersDto);
            myMailSendler.sendEmailWithAttachment(films, mailDto);
        }
        return "redirect:/";
    }

    @GetMapping("search")
    public String search(Model model, @ModelAttribute FilmParametersDto filmParametersDto, BindingResult bindingResult) {
        if (filmParametersDto != null) {
            List<Film> films = filmService.findFromDatabase(filmParametersDto);

            model.addAttribute("films", films);
        }
        return "search";
    }
}
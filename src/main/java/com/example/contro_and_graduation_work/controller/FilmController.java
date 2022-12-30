package com.example.contro_and_graduation_work.controller;

import com.example.contro_and_graduation_work.ControlAndGraduationWorkApplication;
import com.example.contro_and_graduation_work.database.DatabaseModel;
import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.parser.ParserModel;
import com.example.contro_and_graduation_work.service.FilmService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FilmController {

    @Autowired
    public JavaMailSender javaMailSender;
    @Autowired
    private FilmService filmService;

    void sendEmailWithAttachment(String mail, File file, int ii) throws IOException, MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(mail);

        helper.setSubject("Отчёт м вложением");

        helper.setText("TEXT", true);


        if (ii == 1) {
            helper.addAttachment("Отчет.csv", file);
        } else {
            helper.addAttachment("Отчет.xml", file);
        }
        javaMailSender.send(msg);

    }

    @GetMapping({"/"})
    public String film(Model model) {
        List<Film> films = filmService.findAll();
        model.addAttribute("films", films);
        return "index";
    }

    @PostMapping("/send")
    public String sendEmail(Model model, @RequestParam(name = "text", defaultValue = "") String text, @RequestParam(name = "rank[]") Double[] rank, @RequestParam(name = "year[]") Integer[] year, @RequestParam(value = "send", defaultValue = "NONE") String send, @RequestParam("xmlOrCsv") Integer i) {
        System.out.println("MAIL: " + send);
        if (send.equals("NONE")) {
            return "redirect:/";
        }
        DatabaseModel databaseModel = ControlAndGraduationWorkApplication.databaseModel;
        ParserModel parserModel = ControlAndGraduationWorkApplication.parserModel;

        Integer id = null;
        try {
            id = Integer.parseInt(text);
        } catch (Exception e) {

        }


        List<Film> films = null;
        if (id == null) {
            films = databaseModel.searchByWord(text, rank, year);
        } else {
            List<Film> filmes = databaseModel.searchByWord(text, rank, year);

            films = new ArrayList<>();
            Film film = databaseModel.getFilmByKinopoiskId(id);
            if (film != null) {
                films.add(film);
            }
            for (Film f : filmes) {
                films.add(f);
            }
        }//vwtbwuwivmyhbcvn


        //xml
        JSONObject main = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Film film : films) {
            jsonArray.put(film.jsonObject());


        }
        Long id_file = System.currentTimeMillis();
        File file = null;
        if (i == 0) {

            main.put("films", jsonArray);
            String xml = XML.toString(main);
            Path newFilePath = Paths.get("files/test" + id_file + ".xml");
            try {
                Files.createFile(newFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            file = new File("files/test" + id_file + ".xml");
            try {
                FileUtils.writeStringToFile(file, xml, "Cp1251");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        if (i == 1) {

            Path newFilePath = Paths.get("files/test" + id_file + ".csv");
            try {
                Files.createFile(newFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            file = new File("files/test" + id_file + ".csv");

            // Step 5: Produce a comma delimited text from
            // the JSONArray of JSONObjects
            // and write the string to the newly created CSV
            // file

            String csvString = CDL.toString(jsonArray);
            //System.out.println(csvString);
            try {
                FileUtils.writeStringToFile(file, csvString, "Cp1251");


/*
                JsonNode jsonNode = new ObjectMapper().readTree(jsonArray.toString());

                CsvSchema.Builder builder = CsvSchema.builder()
                        .addColumn("id")
                        .addColumn("filmId")
                        .addColumn("filmName")
                        .addColumn("year")
                        .addColumn("rating")
                        .addColumn("description");

                CsvSchema csvSchema = builder.build().withHeader();

                CsvMapper csvMapper = new CsvMapper();
                csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
                csvMapper.writerFor(JsonNode.class)
                        .with(csvSchema)
                        .writeValue(file, jsonNode);

 */
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            sendEmailWithAttachment(send, file, i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    @GetMapping("search")
    public String search(Model model, @RequestParam(name = "text", defaultValue = "") String text, @RequestParam(name = "rank[]") Double[] rank, @RequestParam(name = "year[]") Integer[] year, @RequestParam(name = "send", defaultValue = "NONE") String send, @RequestParam(value = "xmlOrCsv", defaultValue = "100") Integer ii) {

        if (ii != 100) {
            return sendEmail(model, text, rank, year, send, ii);
        }

        DatabaseModel databaseModel = ControlAndGraduationWorkApplication.databaseModel;
        ParserModel parserModel = ControlAndGraduationWorkApplication.parserModel;

        Integer id = null;
        try {
            id = Integer.parseInt(text);
        } catch (Exception e) {

        }


        List<Film> films = null;
        if (id == null) {
            films = databaseModel.searchByWord(text, rank, year);
        } else {
            List<Film> filmes = databaseModel.searchByWord(text, rank, year);

            films = new ArrayList<>();
            Film film = databaseModel.getFilmByKinopoiskId(id);
            if (film != null) {
                films.add(film);
            }
            for (Film f : filmes) {
                films.add(f);
            }
        }//vwtbwuwivmyhbcvn

        if (films.size() <= 1 && id == null) {
            String str = "&keyword=" + text;

            parserModel.parse(str);
            return search(model, text, rank, year, send, ii);
        }

        model.addAttribute("films", films);


        return "search";

    }
}
package com.example.contro_and_graduation_work.MailSendler;

import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.service.FilmService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyMailSendler {

    public final JavaMailSender javaMailSender;

    private final FilmService filmService;

    public void sendEmailWithAttachment(List<Film> films, MailDto mailDto) throws IOException, MessagingException {

        JSONObject main = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Film film : films) {
            jsonArray.put(film.jsonObject());
        }
        main.put("films", jsonArray);


        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(mailDto.getEmail());

        helper.setSubject("Отчёт c вложением");

        helper.setText("TEXT", true);


        if (mailDto.getXmlOrCsv() == XmlOrCsv.CSV) {
            String csvString = CDL.toString(jsonArray);

            /*FileSystemResource file = new FileSystemResource(new File("C:\\XML/file."+mailDto.getXmlOrCsv()));*/

            helper.addAttachment("Отчет.csv", getInputStreamSource(csvString, mailDto.getXmlOrCsv()));


        } else {
            String xml = XML.toString(main);
            /*FileSystemResource file = new FileSystemResource(new File("C:\\XML/file."+mailDto.getXmlOrCsv()));*/
            helper.addAttachment("Отчет.xml", getInputStreamSource(xml, mailDto.getXmlOrCsv()));
        }
        javaMailSender.send(msg);

    }

    public File getInputStreamSource(String files, XmlOrCsv xmlOrCsv) throws IOException {

        File file1 = new File("C:\\XmlOrCsv/file." + xmlOrCsv.toString());
        if (!file1.exists()) {
            file1.createNewFile();
        }
        BufferedWriter file = new BufferedWriter(new FileWriter(file1));
        file.write(files);
        file.close();

        return file1;
    }


}

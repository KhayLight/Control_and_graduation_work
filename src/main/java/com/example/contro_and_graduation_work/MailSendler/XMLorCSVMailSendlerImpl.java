package com.example.contro_and_graduation_work.MailSendler;

import com.example.contro_and_graduation_work.model.Film;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XMLorCSVMailSendlerImpl implements MailSendler, ConverToJSONArray {
    public final JavaMailSender javaMailSender;
    @Value("${mail.subject}")
    private String mailSubject;
    @Value("${mail.txt}")
    private String mailTxt;

    @Override
    public void sendEmailWithAttachment(List<Film> films, MailDto mailDto) throws IOException, MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(mailDto.getEmail());
        helper.setSubject(mailSubject);
        helper.setText(mailTxt, true);

        File file = new File("XmlCsvResults.txt");
        file.delete();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {

            FileChannel fileChannel = randomAccessFile.getChannel();
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096 * 8 * 8);

            if (mailDto.getXmlOrCsv() == XmlOrCsv.CSV) {
                buffer.put(CDL.toString(convert(films)).getBytes());
                helper.addAttachment("CsvResults.csv", file);
            } else {
                buffer.put(XML.toString(convert(films)).getBytes());
                helper.addAttachment("XmlResults.xml", file);
            }
        }
        javaMailSender.send(msg);
    }

    @SneakyThrows
    @Override
    public JSONArray convert(List<Film> films) {
        JSONObject main = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Film film : films) {
            jsonArray.put(film.jsonObject());
        }
        main.put("films", jsonArray);
        return jsonArray;
    }
}

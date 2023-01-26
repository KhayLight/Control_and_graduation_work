package com.example.contro_and_graduation_work.MailSendler;

import com.example.contro_and_graduation_work.model.Film;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ConverToJSONArray {
    @SneakyThrows
    JSONArray convert(List<Film> films);
}

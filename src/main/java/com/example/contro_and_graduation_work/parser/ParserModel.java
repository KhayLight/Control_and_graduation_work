package com.example.contro_and_graduation_work.parser;

import com.example.contro_and_graduation_work.database.DatabaseModel;
import com.example.contro_and_graduation_work.model.Film;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class ParserModel {

    public DatabaseModel databaseModel = null;

    public ParserModel(DatabaseModel databaseModel) {

        this.databaseModel = databaseModel;

    }

    public static String normalizeURL(String urls){
       String startURL = "https://kinopoiskapiunofficial.tech/api/v2.2/films?countries=1&genres=2&order=RATING&type=ALL&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000&page=1"+urls;
       try {
           URL url = new URL(startURL);
           URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
           url = uri.toURL();
           return url.toURI().toASCIIString();
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }
    public static JSONObject getFilms(String urls) {
        try {
            URL url = new URL(normalizeURL(urls));

            //URL url = new URL("https://kinopoiskapiunofficial.tech/api/v2.2/films?countries=1&genres=2&order=RATING&type=ALL&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000&page=1"+urls);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");

            httpConn.setRequestProperty("authority", "kinopoiskapiunofficial.tech");
            httpConn.setRequestProperty("accept", "application/json");
            httpConn.setRequestProperty("accept-language", "ru,en;q=0.9");
            httpConn.setRequestProperty("cookie", "_ym_uid=1672008575890765963; _ym_d=1672008575; _ym_isad=2");
            httpConn.setRequestProperty("referer", "https://kinopoiskapiunofficial.tech/documentation/api/");
            httpConn.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"106\", \"Yandex\";v=\"22\", \"Not;A=Brand\";v=\"99\"");
            httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
            httpConn.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            httpConn.setRequestProperty("sec-fetch-dest", "empty");
            httpConn.setRequestProperty("sec-fetch-mode", "cors");
            httpConn.setRequestProperty("sec-fetch-site", "same-origin");
            httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 YaBrowser/22.11.5.715 Yowser/2.5 Safari/537.36");
            httpConn.setRequestProperty("x-api-key", "fdac05e8-9955-4964-a4ef-076a716d1d5e");

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println(response);
            return new JSONObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getFilmById(Integer id) {
        try {
            URL url = new URL("https://kinopoiskapiunofficial.tech/api/v2.2/films/" + id);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");

            httpConn.setRequestProperty("authority", "kinopoiskapiunofficial.tech");
            httpConn.setRequestProperty("accept", "application/json");
            httpConn.setRequestProperty("accept-language", "ru,en;q=0.9");
            httpConn.setRequestProperty("cookie", "_ym_uid=1672008575890765963; _ym_d=1672008575; _ym_isad=2");
            httpConn.setRequestProperty("referer", "https://kinopoiskapiunofficial.tech/documentation/api/");
            httpConn.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"106\", \"Yandex\";v=\"22\", \"Not;A=Brand\";v=\"99\"");
            httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
            httpConn.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            httpConn.setRequestProperty("sec-fetch-dest", "empty");
            httpConn.setRequestProperty("sec-fetch-mode", "cors");
            httpConn.setRequestProperty("sec-fetch-site", "same-origin");
            httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 YaBrowser/22.11.5.715 Yowser/2.5 Safari/537.36");
            httpConn.setRequestProperty("x-api-key", "fdac05e8-9955-4964-a4ef-076a716d1d5e");

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            return new JSONObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parse(String text) {
        JSONObject jsonObject = getFilms(text);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            int filmId = jsonObject1.getInt("kinopoiskId");
            String filmName = "";
            if(!jsonObject1.isNull("nameRu")){
                 filmName = jsonObject1.getString("nameRu");

            }else {

                if (jsonObject1.has("nameOriginal")) {
                    filmName = jsonObject1.getString("nameOriginal");


                } else {
                    if (!jsonObject1.isNull("nameEn")) {
                        filmName = jsonObject1.getString("nameEn");

                    }
                }
            }

            int year = jsonObject1.getInt("year");
            double raiting = jsonObject1.getDouble("ratingImdb");
            JSONObject filmJSON = getFilmById(filmId);
           // System.out.println(filmJSON);
            String description = "";
            if(jsonObject1.isNull("description")==false){
                description = filmJSON.getString("description");
            }


            Film film = new Film();
            film.setId(0L);
            film.setFilmName(filmName);
            film.setFilmId(filmId);
            film.setYear(year);
            film.setRating(raiting);
            film.setDescription(description);
            databaseModel.saveOrNotFilm(film);

        }

    }

}

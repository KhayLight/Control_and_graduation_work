package com.example.contro_and_graduation_work;


import com.example.contro_and_graduation_work.database.DatabaseModel;
import com.example.contro_and_graduation_work.hibernate.HibernateUtils;
import com.example.contro_and_graduation_work.parser.ParserModel;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ControlAndGraduationWorkApplication {
    public static DatabaseModel databaseModel = null;
    public static ParserModel parserModel = null;

    public static void main(String[] args) {


        Session session = HibernateUtils.sessionFactory.openSession();
        databaseModel = new DatabaseModel();
        parserModel = new ParserModel(databaseModel);
        SpringApplication.run(ControlAndGraduationWorkApplication.class, args);


    }


}

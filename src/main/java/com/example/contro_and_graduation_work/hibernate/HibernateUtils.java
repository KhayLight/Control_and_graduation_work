package com.example.contro_and_graduation_work.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class HibernateUtils {



    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public static SessionFactory sessionFactory = buildSession();

    private static SessionFactory buildSession() {


        return new Configuration().configure(new File("hibernate.cfg.xml")).buildSessionFactory();
    }

    public static void finaly(){
        sessionFactory.close();
    }
}

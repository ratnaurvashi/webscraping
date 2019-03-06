package com.stackroute.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSoupFromStringEx {

    public static void main(String[] args) {

        final String url =
                "https://spring.io/guides/gs/spring-boot/";

        try {
            final Document document = Jsoup.connect(url).get();
            System.out.println(document.outerHtml());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
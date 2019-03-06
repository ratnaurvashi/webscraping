package com.stackroute.service;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSoupFromStringEx {

    public static void main(String[] args) {

        final String url =
                "http://ogp.me/";

        try {
            final Document document = Jsoup.connect(url).get();
            System.out.println("CONTENT: " + document.outerHtml());
            String title = document.title();
            String body = document.body().text();
            System.out.println();
            System.out.println("TITLE: " + title);
            System.out.println();
            System.out.println("BODY: " + body);
            System.out.println();


            Elements metaTags = document.getElementsByTag("meta");
            List<JSONObject> metadata = new ArrayList<>();

            String description = "";
            String keywords = "";
            for (Element mTag : metaTags) {
                JSONObject metatag = new JSONObject();
                String content = mTag.attr("content");
                String name = mTag.attr("name");
                String property = mTag.attr("property");
                if (!name.equals("")) {
                    if (name.equals("description")) {
                        description = document.select("meta[name=description]").first().attr("content");
                    }
                    if (name.equals("keywords")) {
                        keywords = document.select("meta[name=keywords]").first().attr("content");
                    }
                    metatag.put("name", name);
                    metatag.put("content", content);
                    metadata.add(metatag);

                }
                if (!property.equals("")) {
                    if (property.equals("og:description")) {
                        description = document.select("meta[property=og:description]").first().attr("content");
                    }
                    if (name.equals("og:keywords")) {
                        keywords = document.select("meta[property=og:keywords]").first().attr("content");
                    }
                    metatag.put("property", property);
                    metatag.put("content", content);
                    metadata.add(metatag);
                }

            }

            System.out.println("METADATA: " + metadata);
            System.out.println();
            System.out.println("DESCRIPTION : " + description);
            System.out.println();
            System.out.println("KEYWORDS : " + keywords);
            System.out.println();

            int imgcnt = 0;
            Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for (Element image : images) {
                imgcnt++;
            }
            System.out.println("IMAGE COUNT: " + imgcnt);


//            Pattern p = Pattern.compile("<pre>[^>]*>(.+?)</pre>");
//            Matcher m = p.matcher(document.toString());
            Elements code = document.getElementsByTag("pre");
            float codecnt=0;
            for (Element codeElem : code) {
                codecnt++;
            }
            // if we find a match, get the group
//            if (m.find()) {
//                codecnt++;
//            }
            System.out.println();
            System.out.println("NUMBER OF PRE TAGS: "+codecnt);

            Elements alltags = document.getAllElements();
            float tagcount=0;
            for (Element allTag : alltags) {
                tagcount++;
            }

            System.out.println();
            System.out.println("NUMBER OF TOTAL TAGS: "+tagcount);

            float codePercentage = (codecnt/tagcount) * 100;
            System.out.println("CODE PERCENTAGE: "+ codePercentage);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
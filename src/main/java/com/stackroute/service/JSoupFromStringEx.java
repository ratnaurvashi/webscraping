package com.stackroute.service;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JSoupFromStringEx {

    public static void main(String[] args) {

        final String url =
                "https://www.tutorialspoint.com/spring/spring_hello_world_example.htm";

        try {
            final Document document = Jsoup.connect(url).get();
            //getting the whole content of the webpage
            String webContent=document.outerHtml();
            System.out.println("CONTENT: " + webContent);
            //getting the title of the webpage
            String title = document.title();
            System.out.println();
            System.out.println("TITLE: " + title);
            System.out.println();

            //getting the meta tags
            Elements metaTags = document.getElementsByTag("meta");
            List<JSONObject> metadata = new ArrayList<>();

            String description = "";
            String keywords = "";
            for (Element mTag : metaTags) {
                JSONObject metatag = new JSONObject();
                //storing the meta tag properties
                String content = mTag.attr("content");
                String name = mTag.attr("name");
                String property = mTag.attr("property");
                if (!name.equals("")) {
                    if (name.equals("description")) {
                        //getting the description meta tag
                        description = document.select("meta[name=description]").first().attr("content");
                    }
                    if (name.equals("keywords")) {
                        //getting the keywords meta tag
                        keywords = document.select("meta[name=keywords]").first().attr("content");
                    }
                    metatag.put("name", name);
                    metatag.put("content", content);
                    //adding the meta tags in json list metadata
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
                    //adding the meta tags in JSONObject list metadata
                    metadata.add(metatag);
                }

            }

            System.out.println("METADATA: " + metadata);
            System.out.println();
            System.out.println("DESCRIPTION : " + description);
            System.out.println();
            System.out.println("KEYWORDS : " + keywords);
            System.out.println();

            //counting the number of images on the html page
            int imgcnt = 0;
            Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for (Element image : images) {
                imgcnt++;
            }
            System.out.println("IMAGE COUNT: " + imgcnt);

            //calculating all the pre code tags and counting the number of times they occur
            Elements code = document.getElementsByTag("pre").tagName("code");
            float codecnt=0;
            for (Element codeElem : code) {
                codecnt++;
            }
            System.out.println();
            System.out.println("NUMBER OF PRE CODE TAGS: "+codecnt);

            //getting all tags and counting the number of times they occur
            Elements alltags = document.getAllElements();
            float tagcount=0;
            for (Element allTag : alltags) {
                tagcount++;
            }

            System.out.println();
            System.out.println("NUMBER OF TOTAL TAGS: "+tagcount);

            //calculating code percentage
            float codePercentage = (codecnt/tagcount) * 100;
            System.out.println();
            System.out.println("CODE PERCENTAGE: "+ codePercentage);

            //Extracting paragraphs and giving them each a unique id
            Elements paragraphTag = document.getElementsByTag("p");
            int paracnt=0;
            List<JSONObject> paragraphs = new ArrayList<>();
            for (Element paraString : paragraphTag) {
                JSONObject paragraph = new JSONObject();
                paracnt++;
                paragraph.put("paraId",paracnt);
                //extracting content between <p> and </p> tag
                String para = paraString.toString().split("</p>")[0];
                para = para.split("<p>")[1];
                paragraph.put("paragraph",para);
                paragraphs.add(paragraph);
            }
            System.out.println();
            System.out.println("PARAGRAPH JSON: "+paragraphs);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
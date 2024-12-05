package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

public class Gets {
    public void getGets(WebDriver driver) {
        String source = driver.getPageSource();
        //System.out.println(source);
        Document doc = Jsoup.parse(source);
        Elements img = doc.select("img");
        //System.out.println(img);
        Elements enterTeg = doc.select("p");
        String enterText = "";
        String enterImage = "";
        boolean flag = false;
        boolean pointer = false;
        for (Element element : enterTeg) {
            String text = element.text();
            if (text.equals("Попробуйте расширенный персональный гороскоп!")) {
                break;
            }
            if (text.equals("Расшифровка натальной карты")) {
                flag = true;
                continue;
            }
            if (flag) {
                if (pointer) {
                    enterText = enterText + element.text() + "\n";
                }
                pointer = true;
            }
        }
        for (Element image : img) {
            if (!image.attr("border").isEmpty()) {
                enterImage = image.attr("src");
                break;
            }
        }

    }
}

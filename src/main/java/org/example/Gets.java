package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Gets {
    public void getGets(WebDriver driver, Bot bot, Long id) throws UnsupportedEncodingException, MalformedURLException {
        String source = driver.getPageSource();
        //System.out.println(source);
        Document doc = Jsoup.parse(source);
        Elements img = doc.select("img");
        //System.out.println(img);
        Elements enterTeg = doc.select("p");
        //Итоговый текст
        String enterText = "";
        //Итоговое изображение
        String enterImage = "https://www.astroworld.ru/horon/";
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
                enterImage = enterImage + image.attr("src");
                break;
            }
        }
        if (enterText.length() > 4096) {
            int ind = 0;
            int startInd = 0;
            int pointer1 = 0;
            int pointer2 = 0;
            String res = "";
            while (ind != enterText.length() - 1) {
                if (res.length() <= 4096) {
                    res += enterText.charAt(ind);
                    if ((enterText.charAt(ind) == '\\') & (enterText.charAt(ind + 1) == 'n'))
                        pointer1 = ind;
                    else if (enterText.charAt(ind) == '.') {
                        pointer2 = ind;
                    }

                } else {
                    if (pointer1 != 0) {
                        bot.sendText(id, enterText.substring(startInd, pointer1));
                        ind = pointer1 + 2;
                        startInd = ind;
                        pointer1 = 0;
                    } else {
                        bot.sendText(id, enterText.substring(startInd, pointer2 + 1));
                        ind = pointer2 + 1;
                        startInd = ind;
                        pointer2 = 0;
                    }
                    res = "";
                    continue;
                }
                ind++;
            }
            if (!(res.isEmpty()))
                bot.sendText(id, res);
        } else {
            bot.sendText(id, enterText);
        }
        //url encoding, только в кавычках
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(enterImage);
        int lastEnd = 0;

        while (matcher.find()) {
            result.append(enterImage, lastEnd, matcher.start());
            String quotedPart = matcher.group(0);
            String encodedPart;

            try {
                encodedPart = URLEncoder.encode(quotedPart, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            result.append(encodedPart);
            lastEnd = matcher.end();
        }
        result.append(enterImage.substring(lastEnd));
        String enterImageRes = result.toString();
        bot.sendImage(id, enterImageRes);
    }
}

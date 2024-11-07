package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;


public class Natal_chart {
    Bot bot;
    UserStatus status;
    InlineKeyboardButton url = InlineKeyboardButton.builder().text("Tell me more about this").url("https://www.kp.ru/woman/goroskop/natalnaya-karta/?ysclid=m2obv52lm8282521393").build();
    InlineKeyboardMarkup keyboardM2 = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(url)).build();
    public void bottonTap(Long Id, String Call){
        String pg2 = new String("A natal chart is an astrological chart that shows the positions of celestial bodies at the moment of your birth. \uD83C\uDF0C✨ It helps you understand how different planets and zodiac signs influence your personality, character, and life events.\n" +
                "\n" +
                "Each natal chart is as unique as a fingerprint! \uD83D\uDD90\uFE0F\uD83D\uDCAB It consists of:\n" +
                "\n" +
                "1. Zodiac signs: 12 signs, each with its own characteristics and qualities. ♈\uFE0F♉\uFE0F♊\uFE0F\n" +
                "\n" +
                "2. Planets: Each planet is responsible for different aspects of life - from love to career. \uD83C\uDF19☀\uFE0F\uD83D\uDD2D\n" +
                "\n" +
                "3. Houses: 12 houses show in which areas of life the influence of the planets is manifested. \uD83C\uDFE0\uD83C\uDF1F\n" +
                "\n" +
                "Astrologers use the natal chart to analyze and predict events in a person's life, as well as for self-knowledge and personal growth. \uD83C\uDF31\uD83D\uDC96\n" +
                "\n" +
                "If you are wondering how exactly your natal chart can help you, don't hesitate to ask! \uD83D\uDE0A✨");



        switch (Call) {
            case "page1": {
                bot.sendText(Id, "Don't do yet");
                status.setUserState(Id, UserStatus.UserState.ClickedCalculateNatal_Chart);
                break;
            }
            case "page2":{
                status.setUserState(Id, UserStatus.UserState.Clicked_Details);
                bot.sendText(Id, pg2);
                bot.sendMenu(Id, "<tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF12</tg-emoji><b>Choose</b><tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF18</tg-emoji>", keyboardM2);
                break;
            }
            case "page3": {
                bot.sendText(Id, "Do it later");
                break;
            }
        }
    }
}

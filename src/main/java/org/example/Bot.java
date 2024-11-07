package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.Null;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    InlineKeyboardButton page1 = InlineKeyboardButton.builder().text("Calculate natal chart").callbackData("page1").build();
    InlineKeyboardButton page2 = InlineKeyboardButton.builder().text("Read more...").callbackData("page2").build();
    InlineKeyboardButton page3 = InlineKeyboardButton.builder().text("Display aura color").callbackData("page3").build();
    InlineKeyboardButton url = InlineKeyboardButton.builder().text("Tell me more about this").url("https://www.kp.ru/woman/goroskop/natalnaya-karta/?ysclid=m2obv52lm8282521393").build();
    //Long idPol = 1L;
    TokenBot tk=new TokenBot();
    UserStatus status=new UserStatus();
    InlineKeyboardMarkup keyboardM1 = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(page1))
            .keyboardRow(List.of(page2)).keyboardRow(List.of(page3))
            .build();
    InlineKeyboardMarkup keyboardM2 = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(url)).build();

    @Override
    public String getBotUsername() {
        return "tg_natal_chart_bot";
    }

    @Override
    public String getBotToken() {
        return tk.getToken();
    }

    @Override

    public void onUpdateReceived(Update update) {

        if (!update.hasCallbackQuery()) {
            var msg = update.getMessage();
            var user = msg.getFrom();
            var idPol = user.getId();
            System.out.println(idPol);
            System.out.println(user.getId());
            if (update.getMessage().isCommand()) {


                String msgBot = new String("Hello, " + user.getFirstName() + "! I can calculate a natal chart based on your data, namely date of birth, time of birth. If you want to know more about each point, click \"more\". Click on what you want to know!");


                if (msg.getText().equals("/start")) {
                    sendText(user.getId(), msgBot);
                    sendMenu(user.getId(), "<tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF12</tg-emoji><b>Choose</b><tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF18</tg-emoji>", keyboardM1);
                }


            }
        }
        else  {
            String callbackData = update.getCallbackQuery().getData();
            var idPol = update.getCallbackQuery().getFrom().getId();
            System.out.println(idPol);
            // var user=update.getCallbackQuery();
            System.out.println(callbackData);
            bottonTap(idPol, callbackData);
        }


    }
    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


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
                sendText(Id, "Don't do yet");
                status.setUserState(Id, UserStatus.UserState.ClickedCalculateNatal_Chart);
                break;
            }
            case "page2":{
                status.setUserState(Id, UserStatus.UserState.Clicked_Details);
                sendText(Id, pg2);
                sendMenu(Id,"Learn more...",keyboardM2);
                sendMenu(Id, "<tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF12</tg-emoji><b>Choose</b><tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF18</tg-emoji>", keyboardM1);

                break;
            }
            case "page3": {
                sendText(Id, "Do it later");
                break;
            }

        }

    }







}

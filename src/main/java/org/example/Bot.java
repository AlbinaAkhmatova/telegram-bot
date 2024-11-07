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
    TokenBot tk;
    Natal_chart chart;
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
                return;

            }
        }
        else  {
            String callbackData = update.getCallbackQuery().getData();
            var idPol = update.getCallbackQuery().getFrom().getId();
            System.out.println(idPol);
            // var user=update.getCallbackQuery();
            System.out.println(callbackData);
            chart.bottonTap(idPol, callbackData);
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







}

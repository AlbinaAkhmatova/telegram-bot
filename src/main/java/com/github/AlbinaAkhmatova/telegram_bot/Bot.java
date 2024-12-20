package com.github.AlbinaAkhmatova.telegram_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    InlineKeyboardButton page1 = InlineKeyboardButton.builder().text("Рассчитать натальную карту").callbackData("page1").build();
    InlineKeyboardButton page2 = InlineKeyboardButton.builder().text("Подробнее...").callbackData("page2").build();
    //InlineKeyboardButton page3 = InlineKeyboardButton.builder().text("Цвет ауры").callbackData("page3").build();
    InlineKeyboardButton url = InlineKeyboardButton.builder().text("Расскажи мне об этом подробнее").url("https://www.kp.ru/woman/goroskop/natalnaya-karta/?ysclid=m2obv52lm8282521393").build();
    TokenBot tk = new TokenBot();
    UserStatus status = new UserStatus();
    NatalChart natalChart = new NatalChart();
    InlineKeyboardMarkup keyboardM1 = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(page1))
            .keyboardRow(List.of(page2)).build();
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


                String msgBot = new String("Привет, " + user.getFirstName() + "! Я могу рассчитать натальную карту на основе ваших данных, а именно даты и времени рождения. Если вы хотите узнать больше о каждом пункте, нажмите  \"Подробнее...\". Нажмите на то, что вы хотите узнать!\n");


                if (msg.getText().equals("/start")) {
                    sendText(user.getId(), msgBot);
                    sendMenu(user.getId(), "<tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF12</tg-emoji><b>Choose</b><tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF18</tg-emoji>", keyboardM1);
                }
            } else {
                String userMessage = msg.getText();
                UserStatus userState = status;
                saveUserMessage(idPol, userMessage, userState);
            }


        } else {
            String callbackData = update.getCallbackQuery().getData();
            var idPol = update.getCallbackQuery().getFrom().getId();
            System.out.println(idPol);
            // var user=update.getCallbackQuery();
            System.out.println(callbackData);
            bottonTap(idPol, callbackData);
        }


    }

    public static boolean isValidDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            // Парсим дату
            LocalDate date = LocalDate.parse(dateStr, formatter);

            int currentYear = LocalDate.now().getYear();
            int year = date.getYear();
            if (year < 1928 || year > currentYear) {
                return false; // Год не соответствует требованиям
            }

            // Если парсинг успешен, проверяем, что строка соответствует дате
            String formattedDate = date.format(formatter);
            return formattedDate.equals(dateStr); // Сравниваем исходную строку с отформатированной
        } catch (DateTimeParseException e) {
            return false; // Дата некорректна
        }
    }

    public static boolean isValidTime(String timeStr) {
        try {
            // Попробуем распарсить строку в LocalTime
            LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
            return true; // Если парсинг прошел успешно, время корректно
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Метод для сохранения сообщения
    private void saveUserMessage(Long userId, String message, UserStatus userState) {
        System.out.println("Saving message from user " + userId + " with state " + userState + ": " + message);

        switch (userState.getUserState(userId)) {
            case CLICKED_CALCULATE_NATAL_CHART:

                if (isValidDate(message, "dd.MM.yyyy")) {
                    natalChart.setBirthDate(message.split("\\.")[0], message.split("\\.")[1], message.split("\\.")[2]);
                    status.setUserState(userId, UserStatus.UserState.ENTERED_BIRTH_DATE);
                    natalChart.natalChartCalc(userId, status, this);
                } else
                    natalChart.natalChartCalc(userId, userState, this);
                break;
            case ENTERED_BIRTH_TIME:
                if (message.length() < 2) {
                    natalChart.natalChartCalc(userId, userState, this);
                } else {
                    sendText(userId, "Все круто, молодец!");
                    natalChart.setBirthPlace(message);
                    status.setUserState(userId, UserStatus.UserState.ENTERED_BIRTH_PLACE);
                    natalChart.natalChartCalc(userId, status, this);
                }
                break;
            case ENTERED_BIRTH_DATE:
                if (isValidTime(message)) {
                    natalChart.setBirthTime(message.split(":")[0], message.split(":")[1]);
                    status.setUserState(userId, UserStatus.UserState.ENTERED_BIRTH_TIME);
                    natalChart.natalChartCalc(userId, status, this);
                } else
                    natalChart.natalChartCalc(userId, userState, this);
                break;
            default:
                System.out.println("Unknown state!");
                break;
        }
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendImage(long chatId, String imageUrl) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        // Создаем InputFile из URL-адреса изображения
        InputFile inputFile = new InputFile(imageUrl);
        sendPhoto.setPhoto(inputFile);

        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb) {
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public void bottonTap(Long Id, String Call) {
        String pg2 = new String("Натальная карта — это астрологическая карта, " +
                "которая показывает позиции небесных тел в момент вашего рождения. \uD83C\uDF0C✨ Она помогает " +
                "вам понять, как различные планеты и знаки зодиака влияют на вашу личность, " +
                "характер и жизненные события.\n" +
                "\n" +
                "Каждая натальная карта уникальна, как отпечаток пальца! \uD83D\uDD90\uFE0F\uD83D\uDCAB " +
                "Она состоит из:\n" +
                "\n" +
                "1. Знаки зодиака: 12 знаков, каждый из которых имеет свои характеристики и качества. " +
                "♈\uFE0F♉\uFE0F♊\uFE0F\n" +
                "\n" +
                "2. Планеты: Каждая планета отвечает за разные аспекты жизни — от любви до карьеры. " +
                "\uD83C\uDF19☀\uFE0F\uD83D\uDD2D\n" +
                "\n" +
                "3. Дома: 12 домов показывают, в каких областях жизни проявляется влияние планет." +
                " \uD83C\uDFE0\uD83C\uDF1F\n" +
                "\n" +
                "Астрологи используют натальную карту для анализа и предсказания событий в жизни человека, " +
                "а также для самопознания и личностного роста. \uD83C\uDF31\uD83D\uDC96\n" +
                "\n" +
                "Если вы хотите узнать, как именно ваша натальная карта может помочь вам, " +
                "не стесняйтесь спрашивать! \uD83D\uDE0A✨");


        switch (Call) {
            case "page1": {
                status.setUserState(Id, UserStatus.UserState.CLICKED_CALCULATE_NATAL_CHART);
                natalChart.natalChartCalc(Id, status, this);
                break;
            }
            case "page2": {
                status.setUserState(Id, UserStatus.UserState.CLICKED_DETAILS);
                sendText(Id, pg2);
                sendMenu(Id, "Узнать подробнее...", keyboardM2);
                sendMenu(Id, "<tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF12" +
                                "</tg-emoji><b>Choose</b><tg-emoji emoji-id=\"5368324170671202286\">\uD83C\uDF18</tg-emoji>",
                        keyboardM1);

                break;
            }

        }

    }


}




package com.github.AlbinaAkhmatova.telegram_bot;


import java.util.HashMap;
import java.util.Map;

public class NatalChart {
    private String birthDateMonth = null;
    private String birthDateDay = null;
    private String birthDateYear = null;
    private String birthPlace = null;
    private String birthHour = null;
    private String birthMinute = null;
    Map<String, String> monthMap = new HashMap<>();


    public NatalChart() {
        monthMap.put("01", "Январь");
        monthMap.put("02", "Февраль");
        monthMap.put("03", "Март");
        monthMap.put("04", "Апрель");
        monthMap.put("05", "Май");
        monthMap.put("06", "Июнь");
        monthMap.put("07", "Июль");
        monthMap.put("08", "Август");
        monthMap.put("09", "Сентябрь");
        monthMap.put("10", "Октябрь");
        monthMap.put("11", "Ноябрь");
        monthMap.put("12", "Декабрь");

    }

    public void natalChartCalc(Long Id, UserStatus status, Bot bot) {
        if (status.getUserState(Id) == UserStatus.UserState.CLICKED_CALCULATE_NATAL_CHART) {
            bot.sendText(Id, "Пожалуйста, введи свою дату рождения: **.**.**** (Например: 22.02.2024)");
        }

        if (status.getUserState(Id) == UserStatus.UserState.ENTERED_BIRTH_TIME) {
            bot.sendText(Id, "Пожалуйста, введи место, где ты родился: (Например: Екатеринбург)");
        }
        if (status.getUserState(Id) == UserStatus.UserState.ENTERED_BIRTH_DATE) {
            bot.sendText(Id, "Пожалуйста, введи время, в которое ты родился: (Например: 09:00)");
        }
        if (status.getUserState(Id) == UserStatus.UserState.ENTERED_BIRTH_PLACE) {
            bot.sendText(Id, "Все, отлично! Жди свой результат");
            this.getPosTs(bot, Id);
            //status.removeUserState(Id);
        }


    }

    public void getPosTs(Bot bot, Long id) {
        System.out.println(birthDateMonth);
        Posts.getPosts(birthDateDay, birthDateMonth, birthDateYear, birthPlace, birthHour, birthMinute, bot, id);
    }


    public void setBirthDate(String day, String month, String year) {
        this.birthDateDay = day;
        this.birthDateMonth = monthMap.get(month);
        this.birthDateYear = year;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public void setBirthTime(String hour, String minute) {
        this.birthHour = hour;
        this.birthMinute = minute;
    }


}

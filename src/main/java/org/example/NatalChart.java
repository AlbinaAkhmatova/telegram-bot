package org.example;


import java.util.HashMap;
import java.util.Map;

public class NatalChart {
    String BirthDateMonth = null;
    String BirthDateDay = null;
    String BirthDateYear = null;
    String BirthPlace = null;
    private String birthHour =null;
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

    public void NatalChartCalc(Long Id, UserStatus status, Bot bot) {
        if (status.getUserState(Id) == UserStatus.UserState.ClickedCalculateNatal_Chart) {
            bot.sendText(Id, "Пожалуйста, введи свою дату рождения: **.**.**** (Например: 22.02.2024)");

        }

        if (status.getUserState(Id) == UserStatus.UserState.EnteredBirthTime) {
            bot.sendText(Id, "Пожалуйста, введи место, где ты родился: (Например: Екатеринбург)");

        }
        if (status.getUserState(Id) == UserStatus.UserState.EnteredBirthDate) {
            bot.sendText(Id, "Пожалуйста, введи время, в которое ты родился: (Например: 09:00)");
        }
        if (status.getUserState(Id) == UserStatus.UserState.EnteredBirthPlace) {
            bot.sendText(Id,"Все, отлично! Жди свой результат");
            this.getPosTs(bot, Id);
            status.removeUserState(Id);
        }


    }

    public void getPosTs(Bot bot, Long id) {
        System.out.println(BirthDateMonth);
        Posts.getPosts(BirthDateDay,BirthDateMonth,BirthDateYear,BirthPlace,birthHour,birthMinute, bot, id);
    }




    public void setBirthDate(String day,String month,String year) {
        this.BirthDateDay = day;
        this.BirthDateMonth = monthMap.get(month);
        this.BirthDateYear = year;
    }

    public void setBirthPlace(String birthPlace) {
        this.BirthPlace = birthPlace;
    }

    public void setBirthTime(String hour, String minute) {
        this.birthHour = hour;
        this.birthMinute = minute;
    }


}

package org.example;


import org.telegram.telegrambots.meta.api.objects.Update;

public class NatalChart {
    String BirthDateMonth = null;
    String BirthDateDay = null;
    String BirthDateYear = null;
    String BirthPlace = null;
    private String birthHour =null;
    private String birthMinute = null;
    private MonthsRu birthMonth;

    public void NatalChartCalc(Long Id, UserStatus status, Bot bot) {
        if (status.getUserState(Id) == UserStatus.UserState.ClickedCalculateNatal_Chart) {
            bot.sendText(Id, "Пожалуйста, введи свою дату рождения: **.**.**** (Например: 22.02.2024)");

        }

        if (status.getUserState(Id) == UserStatus.UserState.EnteredBirthDate) {
            bot.sendText(Id, "Пожалуйста, введи место, где ты родился: (Например: Екатеринбург)");

        }
        if (status.getUserState(Id) == UserStatus.UserState.EnteredBirthPlace) {
            bot.sendText(Id, "Пожалуйста, введи время, в которое ты родился: (Например: 9:00)");
        }

    }

    public void getPosTs() {
        POSTs.getPosts(BirthDateDay,birthMonth.getMonthName(),BirthDateYear,BirthPlace,birthHour,birthMinute);
    }

    public enum MonthsRu {
        Январь("01"),
        Февраль("02"),
        Март("03"),
        Апрель("04"),
        Май("05"),
        Июнь("06"),
        Июль("07"),
        Август("08"),
        Сентябрь("09"),
        Октябрь("10"),
        Ноябрь("11"),
        Декабрь("12");
        private final String monthNumber;

        MonthsRu(String monthNumber) {
            this.monthNumber = monthNumber;
        }

        public String getMonthNumber() {
            return monthNumber;
        }
        public String getMonthName() {
            return this.name(); // Возвращает имя текущего элемента перечисления
        }
    }


    public void setBirthDate(String day,String month,String year) {
        this.BirthDateDay = day;
        this.BirthDateMonth = month;
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

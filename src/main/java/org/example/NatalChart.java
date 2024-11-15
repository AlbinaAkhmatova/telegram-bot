package org.example;


import org.telegram.telegrambots.meta.api.objects.Update;

public class NatalChart {
    String BirthDate=null;
    String BirthPlace=null;
    public void NatalChartCalc(Long Id, UserStatus status, Bot bot) {
       if (status.getUserState(Id)== UserStatus.UserState.ClickedCalculateNatal_Chart){
           bot.sendText(Id,"Please, write your birthday date: **.**.****");

       }

       if (status.getUserState(Id)== UserStatus.UserState.EnteredBirthDate){
           bot.sendText(Id,"Please, write your birth place(From Russia): (For example: Екатеринбург)");

       }
   }


}

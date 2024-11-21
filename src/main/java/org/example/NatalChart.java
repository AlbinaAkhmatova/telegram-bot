package org.example;


import org.telegram.telegrambots.meta.api.objects.Update;

public class NatalChart {
    String BirthDate=null;
    String BirthPlace=null;
    public void NatalChartCalc(Long Id, UserStatus status, Bot bot) {
       if (status.getUserState(Id)== UserStatus.UserState.ClickedCalculateNatal_Chart){
           bot.sendText(Id,"Пожалуйста, введи свою дату рождения: **.**.**** (Например: 22.02.2024)");

       }

       if (status.getUserState(Id)== UserStatus.UserState.EnteredBirthDate){
           bot.sendText(Id,"Пожалуйста, введи место, где ты родился: (Например: Екатеринбург)");

       }
   }


}

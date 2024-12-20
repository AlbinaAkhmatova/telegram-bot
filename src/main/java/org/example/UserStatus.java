package org.example;

import java.util.HashMap;
import java.util.Map;

public class UserStatus {
    // Определяем состояния пользователей
    public enum UserState {
        OFFLINE,
        Clicked_Details,
        ClickedCalculateNatal_Chart,

        EnteredBirthDate,

        EnteredBirthTime,

        EnteredBirthPlace
    }

    // Храним состояния пользователей
    private Map<Long, UserState> userStates = new HashMap<>();

    // Устанавливаем состояние пользователя
    public void setUserState(Long userId, UserState state) {
        userStates.put(userId, state);
        System.out.println(state);
    }

    // Получаем состояние пользователя
    public UserState getUserState(Long userId) {
        return userStates.getOrDefault(userId, UserState.OFFLINE); // По умолчанию OFFLINE
    }

    // Удаляем состояние пользователя
    public void removeUserState(Long userId) {
        userStates.remove(userId);
    }

}

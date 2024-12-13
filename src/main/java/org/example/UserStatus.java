package org.example;

import java.util.HashMap;
import java.util.Map;

public class UserStatus {
    // определяем состояния пользователей
    public enum UserState {
        OFFLINE,
        CLICKED_DETAILS,
        CLICKED_CALCULATE_NATAL_CHART,
        ENTERED_BIRTH_DATE,
        ENTERED_BIRTH_TIME,
        ENTERED_BIRTH_PLACE
    }


    private Map<Long, UserState> userStates = new HashMap<>();


    public void setUserState(Long userId, UserState state) {
        userStates.put(userId, state);
        System.out.println(state);
    }


    public UserState getUserState(Long userId) {
        return userStates.getOrDefault(userId, UserState.OFFLINE); // По умолчанию OFFLINE
    }


    public void removeUserState(Long userId) {
        userStates.remove(userId);
    }

}

package ru.practicum.ewm_main.event.enums;

import java.util.List;
import java.util.stream.Collectors;

public enum State {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static State from(String state) {
        for (State st : values()) {
            if (st.name().equalsIgnoreCase(state)) {
                return st;
            }
        }
        throw new IllegalArgumentException("Unknown state: " + state);
    }

    public static List<State> fromList(List<String> states) {
        if (states != null) {
            return states.stream().map(State::from).collect(Collectors.toList());
        } else {
            return List.of();
        }
    }
}

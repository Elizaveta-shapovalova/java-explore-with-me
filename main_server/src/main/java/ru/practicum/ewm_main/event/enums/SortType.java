package ru.practicum.ewm_main.event.enums;

public enum SortType {
    EVENT_DATE,
    VIEWS;

    public static SortType from(String sort) {
        if (sort != null) {
            for (SortType sr : values()) {
                if (sr.name().equalsIgnoreCase(sort)) {
                    return sr;
                }
            }
            throw new IllegalArgumentException("Unknown sort: " + sort);
        }
        return null;
    }
}

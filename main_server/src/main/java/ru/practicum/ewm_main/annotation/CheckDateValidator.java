package ru.practicum.ewm_main.annotation;

import ru.practicum.ewm_main.event.dto.EventShortDto;
import ru.practicum.ewm_main.exception.TimeNotValidException;
import ru.practicum.ewm_main.mapper.TimeMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class CheckDateValidator implements ConstraintValidator<StartTimeValid, EventShortDto> {
    @Override
    public void initialize(StartTimeValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(EventShortDto eventDto, ConstraintValidatorContext cxt) {
        if (eventDto.getEventDate() != null && !eventDto.getEventDate().isBlank()) {
            LocalDateTime start = TimeMapper.getDateTime(eventDto.getEventDate());
            if (start.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new TimeNotValidException(String.format("Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value: %s", start));
            }
        }
        return true;
    }
}

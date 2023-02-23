package ru.practicum.ewm_main.user.service;

import org.springframework.data.domain.Page;
import ru.practicum.ewm_main.user.model.User;

import java.util.List;

public interface UserService {
    Page<User> getAll(List<Long> ids, Integer from, Integer size);

    User create(User user);

    void delete(Long id);
}

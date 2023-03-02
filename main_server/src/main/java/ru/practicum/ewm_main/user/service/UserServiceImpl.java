package ru.practicum.ewm_main.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.exception.ObjectNotFoundException;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.util.List;

import static ru.practicum.ewm_main.constant.Constant.SORT_BY_ASC;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public Page<User> getAll(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, SORT_BY_ASC);
        return ids == null || ids.isEmpty() ? userRepository.findAll(pageable) : userRepository.findAllByIdIn(ids, pageable);
    }

    @Transactional
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with id=%d was not found", id)));
        userRepository.deleteById(id);
    }
}
